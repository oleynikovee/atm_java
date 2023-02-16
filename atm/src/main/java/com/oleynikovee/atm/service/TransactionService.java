package com.oleynikovee.atm.service;


import com.oleynikovee.atm.mapper.TransactionMapper;
import com.oleynikovee.atm.model.Transaction;
import com.oleynikovee.atm.repo.jpa.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final UserService userService;
    private final Random random = new Random();

    public List<Transaction> getAllByAccountId(Integer accountId,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountId(accountId));
    }

    public List<Transaction> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId, Integer amountOfMoney,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyGreaterThan(accountId, amountOfMoney));
    }

    public List<Transaction> getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId, Integer amountOfMoney,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndAmountOfMoneyLessThan(accountId, amountOfMoney));
    }

    public List<Transaction> getAllByAccountIdAndCardNumber(Integer accountId,String cardNumber,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModels(repository.getAllByAccountIdAndCardNumber(accountId, cardNumber));
    }

    public Transaction getByAccountIdAndId(Integer accountId, Integer id,String password){
        userService.isCurrentUser(accountId,password);
        return mapper.toModel(repository.getByAccountIdAndId(accountId, id));
    }

    public Integer doTransaction(Transaction transaction,String password){
        userService.isCurrentUser(transaction.getAccountId(),password);
        int id = 0;
        while (repository.existsById(id)) {
            id = idGenerator();
        }
        transaction.setId(id);
        return repository.save(mapper.toEntity(transaction)).getId();
    }
    private int idGenerator() {
        return random.nextInt();
    }

}
