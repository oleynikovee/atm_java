package com.oleynikovee.atm.service;


import com.oleynikovee.atm.mapper.AccountMapper;
import com.oleynikovee.atm.mapper.TransactionMapper;
import com.oleynikovee.atm.mapper.UserMapper;
import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.model.Transaction;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.domain.TransactionEntity;
import com.oleynikovee.atm.repo.jpa.AccountRepository;
import com.oleynikovee.atm.repo.jpa.TransactionRepository;
import com.oleynikovee.atm.repo.jpa.UserRepository;
import com.oleynikovee.atm.web.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository userRepository;


    private final AccountRepository accountRepository;


    private final TransactionRepository transactionRepository;


    private final AccountMapper accountMapper;

    private final TransactionMapper transactionMapper;

    private final UserMapper userMapper;


    public Double getBalance(Long userId) {
        User user = userMapper.toModel(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found.")));
        return user.getAccount().getBalance();
    }

    public String deposit(Long userId, Double amount) {
        String validate = vaidateDepAndWDraw(amount);
        if (!Objects.equals(validate, "ok")) {
            return validate;
        }
        User user = userMapper.toModel(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found.")));
        Account account = user.getAccount();
        account.deposit(amount);
        accountRepository.save(accountMapper.toEntity(account));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromAccount(null);
        transaction.setToAccount(account);
        transaction.setDate(LocalDateTime.now());
        return "Операція успішна. Ідентифцікацій номер операції: " + transactionRepository.save(transactionMapper.toEntity(transaction)).getId();
    }

    public String withdraw(Long userId, Double amount) {
        String validate = vaidateDepAndWDraw(amount);
        if (!Objects.equals(validate, "ok")) {
            return validate;
        }
        User user = userMapper.toModel(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found.")));
        Account account = user.getAccount();
        if (account.getBalance() < amount) {
            throw new RuntimeException("Не достаньо коштів.");
        }
        account.withdraw(amount);
        accountRepository.save(accountMapper.toEntity(account));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromAccount(account);
        transaction.setToAccount(null);
        transaction.setDate(LocalDateTime.now());
        return "Операція успішна. Ідентифцікацій номер операції: " + transactionRepository.save(transactionMapper.toEntity(transaction)).getId();
    }

    public String transfer(Long fromUserId, Long toUserId, Double amount) {
        String validate = validateTransfer(fromUserId, toUserId, amount);
        if (!Objects.equals(validate, "ok")) {
            return validate;
        }
        User fromUser = userMapper.toModel(userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("User not found.")));
        User toUser = userMapper.toModel(userRepository.findById(toUserId).orElseThrow(() -> new RuntimeException("User not found.")));

        Account fromAccount = fromUser.getAccount();
        Account toAccount = toUser.getAccount();

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Не достаньо коштів.");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accountRepository.save(accountMapper.toEntity(fromAccount));
        accountRepository.save(accountMapper.toEntity(toAccount));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        return "Операція успішна. Ідентифцікацій номер операції: " + transactionRepository.save(transactionMapper.toEntity(transaction)).getId();
    }

    public String validateTransfer(Long fromUserId, Long toUserId, Double amount) {
        if (Objects.equals(fromUserId, toUserId)) {
            return "Не можна відправляти із свого аккаунту на свій";
        } else if (amount <= 0) {
            return "Сума відправлення не може бути меншою або дорівнювати 0";
        }
        return "ok";
    }

    public String vaidateDepAndWDraw(Double amount) {
        if (amount <= 0) {
            return "Сума відправлення не може бути меншою або дорівнювати 0";
        }
        return "ok";
    }

}
