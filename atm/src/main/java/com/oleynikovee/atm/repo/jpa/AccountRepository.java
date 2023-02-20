package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.repo.domain.AccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
    AccountEntity findById (Long id);
}
