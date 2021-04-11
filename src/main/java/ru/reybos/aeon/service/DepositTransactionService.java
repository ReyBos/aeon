package ru.reybos.aeon.service;

import org.springframework.stereotype.Service;
import ru.reybos.aeon.model.DepositTransaction;
import ru.reybos.aeon.repository.DepositTransactionRepository;

@Service
public class DepositTransactionService {
    private DepositTransactionRepository depositTransactionRepository;

    public DepositTransactionService(DepositTransactionRepository depositTransactionRepository) {
        this.depositTransactionRepository = depositTransactionRepository;
    }

    public void save(DepositTransaction depositTransaction) {
        depositTransactionRepository.save(depositTransaction);
    }
}
