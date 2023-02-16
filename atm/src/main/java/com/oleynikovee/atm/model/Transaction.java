package com.oleynikovee.atm.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private String cardNumber;
    private LocalDateTime date;
}
