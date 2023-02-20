package com.oleynikovee.atm.service;

import com.oleynikovee.atm.mapper.UserMapper;
import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.model.error.ApplicationException;
import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.domain.UserEntity;
import com.oleynikovee.atm.web.security.ProprietaryPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import com.oleynikovee.atm.repo.jpa.UserRepository;

import java.util.List;
import java.util.Random;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;


    public Long addUser(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()).toLowerCase());
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
        return mapper.toModel(repository.findByLogin(login).orElseThrow(()-> ApplicationException.notFound("User")));
    }

}
