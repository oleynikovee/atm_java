package com.oleynikovee.atm.model.security;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private Integer amountOfMoney;
    private String login;
    private String password;
    private String cardNumber;
    private UserRole role;
}
