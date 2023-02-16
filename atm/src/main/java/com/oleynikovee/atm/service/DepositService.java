package com.oleynikovee.atm.service;


import com.oleynikovee.atm.mapper.DepositMapper;
import com.oleynikovee.atm.model.Deposit;
import com.oleynikovee.atm.repo.jpa.DepositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DepositService {
    private final DepositRepository repository;
    private final DepositMapper mapper;
    private final UserService userService;
    private final Random random = new Random();

    public List<Deposit> getAllByAccountId(Integer accountId,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountId(accountId));
    }

    public List<Deposit> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId, Integer amountOfMoney,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney));
    }

    public List<Deposit> getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId, Integer amountOfMoney,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney));
    }

    public Deposit getByAccountIdAndId(Integer accountId, Integer id,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModel(repository.getByAccountIdAndId(accountId,id));
    }
    public Integer doDeposit(Deposit deposit,String password){
        userService.isCurrentUser(deposit.getAccountId(),password);
        int id = 0;
        while (repository.existsById(id)) {
            id = idGenerator();
        }
        deposit.setId(id);
        return repository.save(mapper.toEntity(deposit)).getId();
    }
    private int idGenerator() {
        return random.nextInt();
    }
}
