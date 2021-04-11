package ru.reybos.aeon.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reybos.aeon.model.DepositTransaction;

public interface DepositTransactionRepository extends CrudRepository<DepositTransaction, Integer> {

}