package com.oleynikovee.atm.service;


import com.oleynikovee.atm.mapper.AccountMapper;
import com.oleynikovee.atm.mapper.TransactionMapper;
import com.oleynikovee.atm.mapper.UserMapper;
import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.model.Transaction;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.jpa.AccountRepository;
import com.oleynikovee.atm.repo.jpa.TransactionRepository;
import com.oleynikovee.atm.repo.jpa.UserRepository;
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

    private Long createTransaction(Double amount, Long fromCardNumber, Long toCardNumber, String type) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromCardNumber(fromCardNumber);
        transaction.setToCardNumber(toCardNumber);
        transaction.setType(type);
        transaction.setDate(LocalDateTime.now());
        transaction.setTransactionContext("Type: " + transaction.getType() + "| from card: " + transaction.getFromCardNumber() + "| to card : " + transaction.getToCardNumber() + "| amount: " + transaction.getAmount());
        return transactionRepository.save(transactionMapper.toEntity(transaction)).getId();
    }

    public String deposit(Long userId, Double amount) {
        String validate = validateDepAndWDraw(amount);
        if (!Objects.equals(validate, "ok")) {
            return validate;
        }
        User user = userMapper.toModel(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found.")));
        Account account = user.getAccount();
        account.deposit(amount);
        accountRepository.save(accountMapper.toEntity(account));

        return "Операція успішна. Ідентифцікацій номер операції: " + createTransaction(amount, account.getCardNumber(), account.getCardNumber(), "Deposit");
    }

    public String withdraw(Long userId, Double amount) {
        String validate = validateDepAndWDraw(amount);
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

        return "Операція успішна. Ідентифцікацій номер операції: " + createTransaction(amount, account.getCardNumber(), account.getCardNumber(), "Withdraw");
    }

    public String transfer(Long fromUserId, Long toCardNumber, Double amount) {
        User fromUser = userMapper.toModel(userRepository.findById(fromUserId).orElseThrow(() -> new RuntimeException("User not found.")));
        String validate = validateTransfer(fromUser.getAccount().getCardNumber(), toCardNumber, amount);
        if (!Objects.equals(validate, "ok")) {
            return validate;
        }

        Account fromAccount = fromUser.getAccount();
        Account toAccount = accountMapper.toModel(accountRepository.findByCardNumber(toCardNumber));


        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Не достаньо коштів.");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accountRepository.save(accountMapper.toEntity(fromAccount));
        accountRepository.save(accountMapper.toEntity(toAccount));

        return "Операція успішна. Ідентифцікацій номер операції: " + createTransaction(amount, fromAccount.getCardNumber(), toCardNumber, "Transfer");
    }

    public String validateTransfer(Long fromCardNumber, Long toCardNumber, Double amount) {
        if (Objects.equals(fromCardNumber, toCardNumber)) {
            return "Не можна відправляти із свого аккаунту на свій";
        } else if (amount <= 0) {
            return "Сума відправлення не може бути меншою або дорівнювати 0";
        }
        return "ok";
    }

    public String validateDepAndWDraw(Double amount) {
        if (amount <= 0) {
            return "Сума відправлення не може бути меншою або дорівнювати 0";
        }
        return "ok";
    }

}
