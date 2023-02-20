package com.oleynikovee.atm.repo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "fromAccount")
    @JsonIgnoreProperties("transactions")
    private List<TransactionEntity> transactions;

    public void deposit(Double amount) {
        balance += amount;
    }

    public void withdraw(Double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new RuntimeException("Insufficient funds.");
        }
    }
}
