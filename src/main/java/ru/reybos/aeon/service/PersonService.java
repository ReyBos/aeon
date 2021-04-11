package ru.reybos.aeon.service;

import org.springframework.stereotype.Service;
import ru.reybos.aeon.model.Person;
import ru.reybos.aeon.repository.PersonRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private DepositService depositService;

    public PersonService(PersonRepository personRepository, DepositService depositService) {
        this.personRepository = personRepository;
        this.depositService = depositService;
    }

    @Transactional
    public Person save(Person person) {
        Person rsl = personRepository.save(person);
        depositService.save(person.getDeposit());
        return rsl;
    }

    @Transactional
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }
}
