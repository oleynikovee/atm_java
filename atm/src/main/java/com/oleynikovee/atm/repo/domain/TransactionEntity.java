package com.oleynikovee.atm.repo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private Long fromCardNumber;

    private Long toCardNumber;
    private String type;

    private LocalDateTime date;

    private String transactionContext;

    @PrePersist
    public void setDate() {
        this.date = LocalDateTime.now();
    }
}
