package com.oleynikovee.atm.model;

import lombok.Data;

@Data
public class TransferRequest {
    private Long toCardNumber;
    private Double Amount;
}
