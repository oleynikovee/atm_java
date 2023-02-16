package com.oleynikovee.atm.repo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name="deposits")
public class DepositEntity {
    @Id
    private Integer id;
    private Integer accountId;
    private Integer amountOfMoney;
    private LocalDateTime date;
}
