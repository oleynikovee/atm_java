package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<UserEntity> findById(Long Id);

    List<UserEntity> getAllBy();

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findUserEntityByAccountId(Long accountId);
}
