package com.oleynikovee.atm.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Transaction {
    private Long id;
    private Double amount;
    private Account fromAccount;
    private Account toAccount;
    private LocalDateTime date;
}
