package com.oleynikovee.atm.service;

import com.oleynikovee.atm.mapper.UserMapper;
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

    public void isCurrentUser(Integer accountId, String password){
        String dbPassword= repository.findById(accountId).map(UserEntity::getPassword).orElseThrow(()-> ApplicationException.notFound("User"));
        if(!new ProprietaryPasswordEncoder().matches(password,dbPassword) ){
            throw ApplicationException.badRequest("Wrong password.");
        }
    }

    public User getByLogin(String login) {
        return mapper.toModel(repository.findByLogin(login).orElseThrow(()-> ApplicationException.notFound("User")));
    }

    private int idGenerator() {
        return random.nextInt();
    }
}
