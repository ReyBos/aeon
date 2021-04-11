package ru.reybos.aeon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.reybos.aeon.model.Deposit;
import ru.reybos.aeon.model.DepositTransaction;
import ru.reybos.aeon.repository.DepositRepository;
import ru.reybos.aeon.repository.DepositTransactionRepository;

@Service
public class DepositService {
    private DepositRepository depositRepository;
    private DepositTransactionRepository depositTransactionRepository;

    public DepositService(
            DepositRepository depositRepository,
            DepositTransactionRepository depositTransactionRepository
    ) {
        this.depositRepository = depositRepository;
        this.depositTransactionRepository = depositTransactionRepository;
    }

    @Transactional
    public void reduceBalance(int depositId, int sum) {
        Deposit deposit = depositRepository.findById(depositId);
        int newBalance = deposit.getBalance() - sum;
        if (newBalance < 0) {
            throw new RuntimeException("Недостаточно средств");
        }
        deposit.setBalance(newBalance);
        DepositTransaction depositTransaction = DepositTransaction.of(sum);
        deposit.addDepositTransaction(depositTransaction);
        depositRepository.save(deposit);
        depositTransactionRepository.save(depositTransaction);
    }

    public void save(Deposit deposit) {
        depositRepository.save(deposit);
    }
}
