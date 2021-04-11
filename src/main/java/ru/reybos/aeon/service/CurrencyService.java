package ru.reybos.aeon.service;

import org.springframework.stereotype.Service;
import ru.reybos.aeon.model.Currency;
import ru.reybos.aeon.repository.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyService {
    private CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Optional<Currency> findByName(String name) {
        return currencyRepository.findByName(name);
    }
}
