package com.oleynikovee.atm.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Transaction {

    private Long id;

    private Double amount;

    private Long fromCardNumber;

    private Long toCardNumber;
    private String type;


    private LocalDateTime date;

    private String transactionContext;
}
