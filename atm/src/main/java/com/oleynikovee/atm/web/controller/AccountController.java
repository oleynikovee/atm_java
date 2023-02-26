package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.security.UserPrincipal;
import com.oleynikovee.atm.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.oleynikovee.atm.config.Constants.API_KEY;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account manager", description = "Gives info about accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserPrincipal loggedUser;

    @SecurityRequirement(name = API_KEY)
    @GetMapping("/transactions/{cardNumber}")
    @Operation(description = "Get transactions by cardNumber")
    public ResponseEntity<List<String>> transactions(@RequestParam Long cardNumber) {
        return ResponseEntity.ok(accountService.getTransactionsByCardNumber(loggedUser.getUserId(), cardNumber));
    }
}