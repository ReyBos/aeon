package ru.reybos.aeon.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "deposit")
public class Deposit {
    public static final int STANDARD_VALUE = 800;
    public static final int REDUCE_VALUE = 110;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "balance")
    private int balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deposit")
    private List<DepositTransaction> transactions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public void addDepositTransaction(DepositTransaction transaction) {
        transaction.setDeposit(this);
        transactions.add(transaction);
    }

    public static Deposit of(int balance, Currency currency) {
        Deposit deposit = new Deposit();
        deposit.balance = balance;
        deposit.currency = currency;
        return deposit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<DepositTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DepositTransaction> transactions) {
        this.transactions = transactions;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deposit deposit = (Deposit) o;
        return id == deposit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}