package com.oleynikovee.atm.model;

import lombok.Data;

@Data
public class Deposit {
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private String date;
}
