package com.oleynikovee.atm.model;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private Double balance;

    private Long cardNumber;
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
