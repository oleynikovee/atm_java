package com.oleynikovee.atm.model;

import lombok.Data;

@Data
public class TransferRequest {
    private Long toUserId;
    private Double Amount;
}
