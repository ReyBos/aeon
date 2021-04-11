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
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.repository.PersonRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository repository;

    @AfterEach
    public void clear() {
        repository.deleteAll();
    }

    private Person createTestPerson(String login, String password) {
        Person emp = Person.of(login, password);
        return repository.save(emp);
    }

    @Test
    public void whenGetAllPersonThenStatus403() throws Exception {
        mockMvc.perform(get("/person/"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenCreatedThenStatus201() throws Exception {
        Person person = Person.of("Login", "Password");
        mockMvc.perform(
                post("/person/")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("Login"));
    }

    @Test
    public void whenLoginAndGetAllPersonThenStatus200() throws Exception {
        Person person = createTestPerson("login", "password");
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
        mockMvc.perform(get("/person/")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void whenLoginThenStatus401() throws Exception {
        Person person = createTestPerson("login", "password");
        mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}