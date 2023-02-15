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
    private final Random random = new Random();

    public List<Deposit> getAllByAccountId(Integer accountId){
        return mapper.toModels(repository.getAllByAccountId(accountId));
    }

    public List<Deposit> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId, Integer amountOfMoney){
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney));
    }

    public List<Deposit> getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId, Integer amountOfMoney){
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney));
    }

    public Deposit getByAccountIdAndId(Integer accountId, Integer id){
        return mapper.toModel(repository.getByAccountIdAndId(accountId,id));
    }
    public Integer doDeposit(Deposit deposit){
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
