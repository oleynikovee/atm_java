package com.oleynikovee.atm.service;

import com.oleynikovee.atm.mapper.UserMapper;
import com.oleynikovee.atm.model.error.ApplicationException;
import com.oleynikovee.atm.model.security.User;
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
    private final Random random = new Random();

    public Integer addUser(User user) {
        if (repository.existsByLogin(user.getLogin())) {
            throw ApplicationException.badRequest(format("User with login: %s already exists!!!", user.getLogin()));
        }
        int id = 0;
        while (repository.existsById(id)) {
            id = idGenerator();
        }
        user.setId(id);
        user.setPassword(DigestUtils.md5Hex(user.getPassword()).toLowerCase());
        return repository.save(mapper.toEntity(user)).getId();
    }

    public User getUserById(Integer userId) {
        User user = mapper.toModel(repository.findById(userId).orElseThrow(() -> ApplicationException.notFound("User")));
        user.setPassword(null);
        return user;
    }

    public List<User> getAll() {
        List<User> users = mapper.toModels(repository.getAllBy());
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    public void deleteUser(Integer userId) {
        if (!repository.existsById(userId)) {
            throw ApplicationException.notFound("User");
        }
        repository.deleteById(userId);
    }

    public boolean isCurrentUser(Integer accountId, String password){
        User user= mapper.toModel(repository.findById(accountId).orElseThrow(()-> ApplicationException.notFound("User")));
        if(user.getPassword()==password){
            return true;
        }else{
            return false;
        }
    }

    public User getByLogin(String login) {
        return mapper.toModel(repository.findByLogin(login).orElseThrow(()-> ApplicationException.notFound("User")));
    }

    private int idGenerator() {
        return random.nextInt();
    }
}
