package com.oleynikovee.atm.service;

import com.oleynikovee.atm.mapper.WithdrawMapper;
import com.oleynikovee.atm.model.Withdraw;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.jpa.WithdrawRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final WithdrawRepository repository;
    private final WithdrawMapper mapper;
    private final UserService userService;
    private final Random random = new Random();

    public List<Withdraw> getAllByAccountId(Integer accountId, String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountId(accountId));
    }

    public List<Withdraw> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId, Integer amountOfMoney,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney));
    }

    public List<Withdraw> getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId, Integer amountOfMoney,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney));
    }

    public Withdraw getByAccountIdAndId(Integer accountId, Integer id,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModel(repository.getByAccountIdAndId(accountId,id));
    }

    public Integer doWithdraw(Withdraw withdraw,String password){
        userService.isCurrentUser(withdraw.getAccountId(),password);
        int id = 0;
        while (repository.existsById(id)) {
            id = idGenerator();
        }
        withdraw.setId(id);
        return repository.save(mapper.toEntity(withdraw)).getId();
    }
    private int idGenerator() {
        return random.nextInt();
    }

}
