package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.Withdraw;
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
@RequestMapping("/withdraw")
@RequiredArgsConstructor
@Tag(name = "Withdraws manager", description = "Gives info about withdraws on account")
public class WithdrawController {
    private final UserService userService;
    private final WithdrawService withdrawService;

    @GetMapping(path = "/{accountId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Withdraws by id or null if invalid password")
    public ResponseEntity<List<Withdraw>> getAllByAccountId(@RequestParam String password, @RequestParam Integer accountId) {
        return ResponseEntity.ok(withdrawService.getAllByAccountId(accountId, password));
    }

    @GetMapping(path = "/{accountId}/greater/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Withdraws where amountOfMoney greater then value was input or null if invalid password")
    public ResponseEntity<List<Withdraw>> getAllByAccountIdAndAmountOfMoneyGreaterThan(@RequestParam String password, @RequestParam Integer accountId, @RequestParam Integer amountOfMoney) {
        userService.isCurrentUser(accountId, password);
        return ResponseEntity.ok(withdrawService.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney, password));
    }

    @GetMapping(path = "/{accountId}/less/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Withdraws where amountOfMoney less then value was input or null if invalid password")
    public ResponseEntity<List<Withdraw>> getAllByAccountIdAndAmountOfMoneyLessThan(@RequestParam String password, @RequestParam Integer accountId, @RequestParam Integer amountOfMoney) {
        return ResponseEntity.ok(withdrawService.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney, password));
    }

    @GetMapping(path = "/{accountId}/{id}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and id of Withdraw, and password, that was used for creating accountId-> return Withdraw by id or null if invalid password")
    public ResponseEntity<Withdraw> getByAccountIdAndId(@RequestParam String password, @RequestParam Integer accountId, @RequestParam Integer id) {
        return ResponseEntity.ok(withdrawService.getByAccountIdAndId(accountId, id, password));
    }

    @PutMapping(path = "/{accountId}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Put your withdraw into account using password and query -> return ID")
    public ResponseEntity<Integer> doWithdraws(@RequestBody Withdraw withdraw, @RequestParam String password) {
        return ResponseEntity.accepted().body(withdrawService.doWithdraw(withdraw,password));
    }
}
