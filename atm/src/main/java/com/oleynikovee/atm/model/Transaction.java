package com.oleynikovee.atm.model;

import lombok.Data;

@Data
public class Transaction {
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private String cardNumber;
    private String date;
}
