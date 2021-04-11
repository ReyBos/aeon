package ru.reybos.aeon.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.reybos.aeon.helper.serialize.DepositSerializer;
import ru.reybos.aeon.helper.serialize.PersonSerializer;
import ru.reybos.aeon.model.Currency;
import ru.reybos.aeon.model.Deposit;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.service.CurrencyService;
import ru.reybos.aeon.service.PersonService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Deposit.class, new DepositSerializer())
            .registerTypeAdapter(Person.class, new PersonSerializer())
            .create();
    private PersonService personService;
    private CurrencyService currencyService;
    private BCryptPasswordEncoder encoder;

    public PersonController(
            PersonService personService,
            CurrencyService currencyService,
            BCryptPasswordEncoder encoder
    ) {
        this.personService = personService;
        this.currencyService = currencyService;
        this.encoder = encoder;
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        Optional<Currency> currency = currencyService.findByName(Currency.USD);
        if (currency.isEmpty()) {
            throw new RuntimeException("Ошибка загрузки валюты");
        }
        Deposit deposit = Deposit.of(Deposit.STANDARD_VALUE, currency.get());
        person.addDeposit(deposit);
        Person rsl = this.personService.save(person);
        return new ResponseEntity<>(
                gson.toJson(rsl),
                HttpStatus.CREATED
        );
    }

    /**
     * метод для тестирования доступа
     */
    @GetMapping("/")
    public List<Person> findAll() {
        return Collections.emptyList();
    }
}
