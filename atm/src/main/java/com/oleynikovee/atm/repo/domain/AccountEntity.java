package com.oleynikovee.atm.repo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Random;

@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "cardNumber")
    private Long cardNumber;

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

    public Long generateCreditCardNumber() {
        Random random = new Random();
        String cardNumber = "5";
        for (int i = 0; i < 15; i++) {
            int digit = random.nextInt(10);
            cardNumber += digit;
        }
        return Long.parseLong(cardNumber);
    }
}
