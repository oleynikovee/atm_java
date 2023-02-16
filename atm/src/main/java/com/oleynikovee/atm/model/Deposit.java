package com.oleynikovee.atm.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Deposit {
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private LocalDateTime date;
}
