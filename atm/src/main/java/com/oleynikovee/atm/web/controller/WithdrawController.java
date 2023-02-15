package com.oleynikovee.atm.web.controller;

import com.oleynikovee.atm.model.Withdraw;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.service.UserService;
import com.oleynikovee.atm.service.WithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
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
    public ResponseEntity<List<Withdraw>> getAllByAccountId (@RequestBody Integer accountId,@RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(withdrawService.getAllByAccountId(accountId));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
    @GetMapping(path = "/{accountId}/greater/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Withdraws where amountOfMoney greater then value was input or null if invalid password")
    public ResponseEntity<List<Withdraw>> getAllByAccountIdAndAmountOfMoneyGreaterThan (@RequestBody Integer accountId,@RequestBody Integer amountOfMoney,@RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(withdrawService.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
    @GetMapping(path = "/{accountId}/less/{amountOfMoney}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and password, that was used for creating accountId-> return List of Withdraws where amountOfMoney less then value was input or null if invalid password")
    public ResponseEntity<List<Withdraw>> getAllByAccountIdAndAmountOfMoneyLessThan (@RequestBody Integer accountId,@RequestBody Integer amountOfMoney,@RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(withdrawService.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
    @GetMapping(path = "/{accountId}/{id}")
    @SecurityRequirement(name = API_KEY)
    @Operation(description = "Need accountId and id of Withdraw, and password, that was used for creating accountId-> return Withdraw by id or null if invalid password")
    public ResponseEntity<Withdraw> getByAccountIdAndId (@RequestBody Integer accountId,@RequestBody Integer id, @RequestBody String password) {
        if(userService.isCurrentUser(accountId,password)) {
            return ResponseEntity.accepted().body(withdrawService.getByAccountIdAndId(accountId,id));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }

    @PutMapping(path = "/{accountId}")
    @SecurityRequirement(name=API_KEY)
    @Operation(description = "Put your withdraw into account using password and query -> return ID")
    public ResponseEntity<Integer> doWithdraws(@RequestBody Withdraw withdraw, @RequestBody String password){
        if(userService.isCurrentUser((withdraw.getAccountId()),password)) {
            return ResponseEntity.accepted().body(withdrawService.doWithdraw(withdraw));
        }else{
            return ResponseEntity.accepted().body(null);
        }
    }
}
