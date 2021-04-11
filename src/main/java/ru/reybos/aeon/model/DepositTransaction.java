package ru.reybos.aeon.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "deposit_transaction")
public class DepositTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sum")
    private int sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    public static DepositTransaction of(int sum) {
        DepositTransaction depositTransaction = new DepositTransaction();
        depositTransaction.sum = sum;
        return depositTransaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepositTransaction that = (DepositTransaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
