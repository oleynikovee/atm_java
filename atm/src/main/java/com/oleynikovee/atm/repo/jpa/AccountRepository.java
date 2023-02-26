package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
    AccountEntity findById(Long id);

    AccountEntity findByCardNumber(Long cardNumber);
}
