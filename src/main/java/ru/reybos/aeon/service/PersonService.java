package ru.reybos.aeon.service;

import org.springframework.stereotype.Service;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.repository.PersonRepository;

@Service
public class PersonService {
    private PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person save(Person person) {
        return repository.save(person);
    }
}
