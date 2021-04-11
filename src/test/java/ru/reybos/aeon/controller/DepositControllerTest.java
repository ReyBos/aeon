package ru.reybos.aeon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.reybos.aeon.Main;
import ru.reybos.aeon.model.Deposit;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.repository.DepositRepository;
import ru.reybos.aeon.repository.DepositTransactionRepository;
import ru.reybos.aeon.repository.PersonRepository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class DepositControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DepositTransactionRepository depositTransactionRepository;

    @Autowired
    private DepositRepository depositRepository;

    @AfterEach
    public void clear() {
        depositTransactionRepository.deleteAll();
        depositRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void whenPaymentOnceThenStatus200() throws Exception {
        Person person = Person.of("Login", "Password");

        mockMvc.perform(
                post("/person/")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getHeader("Authorization");

        mockMvc.perform(put("/payment/")
                .header("Authorization", token))
                .andExpect(status().isOk());

        person = personRepository.findByLogin(person.getLogin()).get();
        int expectedBalance = Deposit.STANDARD_VALUE - Deposit.REDUCE_VALUE;
        assertThat(person.getDeposit().getBalance(), is(expectedBalance));
        assertThat(person.getDeposit().getTransactions().size(), is(1));
    }

    @Test
    public void whenManyPaymentsThenCorrectBalance() throws Exception {
        Person person = Person.of("Login", "Password");

        mockMvc.perform(
                post("/person/")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getHeader("Authorization");

        int expectedBalance = Deposit.STANDARD_VALUE;
        int expectedTransactions = 0;
        ExecutorService pool = Executors.newCachedThreadPool();
        Collection<Future<?>> futures = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            if (expectedBalance - Deposit.REDUCE_VALUE >= 0) {
                expectedBalance -= Deposit.REDUCE_VALUE;
                expectedTransactions++;
            }
            futures.add(pool.submit(() -> mockMvc.perform(put("/payment/")
                    .header("Authorization", token))
                    .andReturn()));
        }
        for (Future<?> future:futures) {
            future.get();
        }

        person = personRepository.findByLogin(person.getLogin()).get();
        assertThat(person.getDeposit().getBalance(), is(expectedBalance));
        assertThat(person.getDeposit().getTransactions().size(), is(expectedTransactions));
    }
}