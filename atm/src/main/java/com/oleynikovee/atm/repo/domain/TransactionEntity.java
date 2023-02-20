package com.oleynikovee.atm.repo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties("transactions")
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private AccountEntity fromAccount;

    @JsonIgnoreProperties("transactions")
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private AccountEntity toAccount;

    private LocalDateTime date;

    @PrePersist
    public void setDate() {
        this.date = LocalDateTime.now();
    }
}
