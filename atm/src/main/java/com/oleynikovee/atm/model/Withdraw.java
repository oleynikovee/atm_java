package com.oleynikovee.atm.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Withdraw {
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private LocalDateTime date;
}
