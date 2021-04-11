package ru.reybos.aeon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.service.PersonService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private PersonService personService;
    private BCryptPasswordEncoder encoder;

    public PersonController(PersonService personService, BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.encoder = encoder;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<Person>(
                this.personService.save(person),
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
