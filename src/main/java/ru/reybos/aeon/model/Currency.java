package ru.reybos.aeon.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class Currency {
    public static final String USD = "USD";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency currency = (Currency) o;
        return id == currency.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
