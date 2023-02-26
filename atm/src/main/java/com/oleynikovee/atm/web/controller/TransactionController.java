package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.DepositRequest;
import com.oleynikovee.atm.model.TransferRequest;
import com.oleynikovee.atm.model.WithdrawRequest;
import com.oleynikovee.atm.model.security.UserPrincipal;
import com.oleynikovee.atm.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.oleynikovee.atm.config.Constants.API_KEY;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Tag(name = "Transaction manager", description = "Gives info about transactions on account")
public class TransactionController {
    private final TransactionService transactionService;
    private final UserPrincipal loggedUser;

    @SecurityRequirement(name = API_KEY)
    @PostMapping("/withdraw")
    public String withdraw(@RequestBody WithdrawRequest request) {
        return transactionService.withdraw(loggedUser.getUserId(), request.getAmount());
    }

    @SecurityRequirement(name = API_KEY)
    @PostMapping("/deposit")
    public String deposit(@RequestBody DepositRequest request) {
        return transactionService.deposit(loggedUser.getUserId(), request.getAmount());
    }

    @SecurityRequirement(name = API_KEY)
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(loggedUser.getUserId(), request.getToCardNumber(), request.getAmount());
    }

    @SecurityRequirement(name = API_KEY)
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance() {
        return ResponseEntity.ok(transactionService.getBalance(loggedUser.getUserId()));
    }

}