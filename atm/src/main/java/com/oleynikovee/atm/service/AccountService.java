package com.oleynikovee.atm.service;

import com.oleynikovee.atm.mapper.AccountMapper;
import com.oleynikovee.atm.mapper.TransactionMapper;
import com.oleynikovee.atm.mapper.UserMapper;
import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.domain.TransactionEntity;
import com.oleynikovee.atm.repo.jpa.AccountRepository;
import com.oleynikovee.atm.repo.jpa.TransactionRepository;
import com.oleynikovee.atm.repo.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public List<String> getTransactionsByCardNumber(Long userId, Long cardNumber) {
        List<String> transactionContexts = new ArrayList<>();
        Account account = accountMapper.toModel(accountRepository.findByCardNumber(cardNumber));
        Account accountVal = accountMapper.toModel(accountRepository.findById(userId));
        User user = userMapper.toModel(userRepository.findUserEntityByAccountId(account.getId()).orElseThrow(() -> new RuntimeException("User not found.")));
        if (!Objects.equals(userId, user.getId())) {
            return transactionContexts;
        }
        List<TransactionEntity> transactions = transactionRepository.getAllByFromCardNumber(cardNumber);
        transactions.forEach(transaction -> {
            transactionContexts.add(transaction.getTransactionContext());
        });
        return transactionContexts;
    }
}
