package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.Deposit;
import com.oleynikovee.atm.model.Transaction;
import com.oleynikovee.atm.model.Withdraw;
import com.oleynikovee.atm.service.DepositService;
import com.oleynikovee.atm.service.TransactionService;
import com.oleynikovee.atm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.oleynikovee.atm.config.Constants.API_KEY;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Tag(name = "Transaction manager", description = "Gives info about transactions on account")
public class TransactionController {
    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping(path = "/{accountId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Transactions by id or null if invalid password")
    public ResponseEntity<List<Transaction>> getAllByAccountId(@RequestParam Integer accountId, @RequestParam String password) {
        return ResponseEntity.ok(transactionService.getAllByAccountId(accountId, password));
    }

    @GetMapping(path = "/{accountId}/greater/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Transactions where amountOfMoney greater then value was input or null if invalid password")
    public ResponseEntity<List<Transaction>> getAllByAccountIdAndAmountOfMoneyGreaterThan(@RequestParam Integer accountId, @RequestParam Integer amountOfMoney, @RequestParam String password) {
        return ResponseEntity.ok(transactionService.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney, password));
    }

    @GetMapping(path = "/{accountId}/less/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Transactions where amountOfMoney less then value was input or null if invalid password")
    public ResponseEntity<List<Transaction>> getAllByAccountIdAndAmountOfMoneyLessThan(@RequestParam Integer accountId, @RequestParam Integer amountOfMoney, @RequestParam String password) {
        return ResponseEntity.ok(transactionService.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney, password));
    }

    @GetMapping(path = "/{accountId}/card/{cardNumber}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId + card number and password, that was used for creating accountId-> return list of transactions by card number")
    public ResponseEntity<List<Transaction>> getAllByAccountIdAndAmountOfMoneyLessThan(@RequestParam Integer accountId, @RequestParam String cardNumber, @RequestParam String password) {
        return ResponseEntity.ok(transactionService.getAllByAccountIdAndCardNumber(accountId, cardNumber, password));
    }

    @GetMapping(path = "/{accountId}/{id}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and id of Transaction, and password, that was used for creating accountId-> return transaction by id or null if invalid password")
    public ResponseEntity<Transaction> getByAccountIdAndId(@RequestParam Integer accountId, @RequestParam Integer id, @RequestParam String password) {
        return ResponseEntity.ok(transactionService.getByAccountIdAndId(accountId, id, password));
    }

    @PutMapping(path = "/{accountId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Put your transaction into account using password and query -> return ID")
    public ResponseEntity<Integer> doTransaction(@RequestBody Transaction transaction, @RequestParam String password) {
        return ResponseEntity.accepted().body(transactionService.doTransaction(transaction, password));
    }
}