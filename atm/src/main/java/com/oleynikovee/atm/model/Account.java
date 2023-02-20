package com.oleynikovee.atm.model;

import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.domain.TransactionEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account {
    private Long id;
    private Double balance;
    private List<TransactionEntity> transactions = new ArrayList<>();

    public void deposit(Double amount) {
        balance += amount;
    }

    public void withdraw(Double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new RuntimeException("Не достатньо коштів.");
        }
    }
}
