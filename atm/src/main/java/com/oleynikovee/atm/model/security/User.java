package com.oleynikovee.atm.model.security;

import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.repo.domain.AccountEntity;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String login;
    private String password;
    private Account account;
    private UserRole userRole   ;
}
