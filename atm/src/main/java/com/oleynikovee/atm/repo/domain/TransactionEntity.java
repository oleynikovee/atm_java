package com.oleynikovee.atm.repo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "transactions")
public class TransactionEntity {
    @Id
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private String cardNumber;
    private String date;
}
