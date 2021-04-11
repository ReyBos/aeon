package ru.reybos.aeon.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reybos.aeon.model.Deposit;

public interface DepositRepository extends CrudRepository<Deposit, Integer> {
    Deposit findById(int id);
}
