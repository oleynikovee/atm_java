package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity,Integer> {
    List<TransactionEntity> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
}
