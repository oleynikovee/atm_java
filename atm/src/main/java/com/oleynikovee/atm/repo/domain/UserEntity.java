package com.oleynikovee.atm.repo.domain;

import com.oleynikovee.atm.model.security.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    private Integer id;
    private Integer amountOfMoney;
    private String login;
    private String password;
    private String cardNumber;
    private UserRole role;
}
