package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findByFromCardNumberAndToCardNumber(Long fromCardNumber, Long toCardNumber);

    List<TransactionEntity> findByType(String type);

    List<TransactionEntity> getAllByFromCardNumber(Long fromCardNumber);
}
