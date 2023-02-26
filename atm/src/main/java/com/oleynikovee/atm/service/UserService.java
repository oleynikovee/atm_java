package com.oleynikovee.atm.service;

import com.oleynikovee.atm.mapper.AccountMapper;
import com.oleynikovee.atm.mapper.UserMapper;
import com.oleynikovee.atm.model.error.ApplicationException;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.domain.AccountEntity;
import com.oleynikovee.atm.repo.jpa.AccountRepository;
import com.oleynikovee.atm.repo.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private boolean validateCardNumber(Long cardNumber) {
        Long buffer = 0L;
        try {
            buffer = accountRepository.findByCardNumber(cardNumber).getCardNumber();
        } catch (NullPointerException exception) {
            return false;
        }
        return true;
    }

    public Long addUser(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()).toLowerCase());
        AccountEntity account = accountMapper.toEntity(user.getAccount());
        Long cardNumber = account.generateCreditCardNumber();
        boolean buffer = validateCardNumber(cardNumber);
        while (buffer) {
            cardNumber = account.generateCreditCardNumber();
            buffer = validateCardNumber(cardNumber);
        }
        account.setCardNumber(cardNumber);
        accountRepository.save(account);
        user.setAccount(accountMapper.toModel(account));
        return repository.save(mapper.toEntity(user)).getId();
    }

    public User getUserById(Long userId) {
        User user = mapper.toModel(repository.findById(userId).orElseThrow(() -> ApplicationException.notFound("User")));
        user.setPassword(null);
        return user;
    }

    public List<User> getAll() {
        List<User> users = mapper.toModels(repository.getAllBy());
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    public void deleteUser(Long userId) {
        if (!repository.existsById(userId)) {
            throw ApplicationException.notFound("User");
        }
        repository.deleteById(userId);
    }


    public User getByLogin(String login) {
        return mapper.toModel(repository.findByLogin(login).orElseThrow(() -> ApplicationException.notFound("User")));
    }

}
