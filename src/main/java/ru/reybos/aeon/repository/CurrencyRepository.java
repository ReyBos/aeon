package ru.reybos.aeon.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reybos.aeon.model.Currency;

import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
    Optional<Currency> findByName(String name);
}