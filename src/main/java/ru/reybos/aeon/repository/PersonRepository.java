package ru.reybos.aeon.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reybos.aeon.model.Person;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Optional<Person> findByLogin(String login);
}