package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    boolean existsByLogin(String login);
    boolean existsById(int id);
    List<UserEntity> getAllBy();
    Optional<UserEntity> findByLogin(String login);

}
