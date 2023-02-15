package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.Deposit;
import com.oleynikovee.atm.model.Transaction;
import com.oleynikovee.atm.model.Withdraw;
import com.oleynikovee.atm.service.DepositService;
import com.oleynikovee.atm.service.UserService;
import com.oleynikovee.atm.service.WithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.oleynikovee.atm.config.Constants.API_KEY;

@RestController
@RequestMapping("/deposit")
@RequiredArgsConstructor
@Tag(name = "Deposit manager", description = "Gives info about deposits on account")
public class DepositController {
    private final UserService userService;
    private final DepositService depositService;
    @GetMapping(path = "/{accountId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Deposits by id or null if invalid password")
    public ResponseEntity<List<Deposit>> getAllByAccountId (@RequestBody Integer accountId, @RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(depositService.getAllByAccountId(accountId));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
    @GetMapping(path = "/{accountId}/greater/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Deposits where amountOfMoney greater then value was input or null if invalid password")
    public ResponseEntity<List<Deposit>> getAllByAccountIdAndAmountOfMoneyGreaterThan (@RequestBody Integer accountId,@RequestBody Integer amountOfMoney,@RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(depositService.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
    @GetMapping(path = "/{accountId}/less/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Deposits where amountOfMoney less then value was input or null if invalid password")
    public ResponseEntity<List<Deposit>> getAllByAccountIdAndAmountOfMoneyLessThan (@RequestBody Integer accountId,@RequestBody Integer amountOfMoney,@RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(depositService.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
    @GetMapping(path = "/{accountId}/{id}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and id of Deposit, and password, that was used for creating accountId-> return Deposit by id or null if invalid password")
    public ResponseEntity<Deposit> getByAccountIdAndId (@RequestBody Integer accountId,@RequestBody Integer id, @RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(depositService.getByAccountIdAndId(accountId,id));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }

    @PutMapping(path = "/{accountId}")
    @SecurityRequirement(name=API_KEY)
    @Operation(description = "Put your deposit into account using password and query -> return ID")
    public ResponseEntity<Integer> doDeposit(@RequestBody Deposit deposit, @RequestBody String password){
        if(userService.isCurrentUser((deposit.getAccountId()),password)) {
            return ResponseEntity.accepted().body(depositService.doDeposit(deposit));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
}