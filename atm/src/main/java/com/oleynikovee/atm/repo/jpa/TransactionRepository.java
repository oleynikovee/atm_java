package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.DepositEntity;
import com.oleynikovee.atm.repo.domain.TransactionEntity;
import com.oleynikovee.atm.repo.domain.WithdrawEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface TransactionRepository extends CrudRepository<TransactionEntity,Integer> {
    boolean existsById(int id);
    List<TransactionEntity> getAllByAccountId(Integer accountId);

    List<TransactionEntity> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId,Integer amountOfMoney);
    List<TransactionEntity> getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId,Integer amountOfMoney);
    List<TransactionEntity> getAllByAccountIdAndCardNumber(Integer accountId, String cardNumber);
    TransactionEntity getByAccountIdAndId(Integer accountId, Integer id);
}
