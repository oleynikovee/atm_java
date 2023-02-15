package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.DepositEntity;
import com.oleynikovee.atm.repo.domain.WithdrawEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface DepositRepository extends CrudRepository<DepositEntity, Integer> {
    boolean existsById(int id);
    List<DepositEntity> getAllByAccountId(Integer accountId);
    List<DepositEntity> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId,Integer amountOfMoney);
    List<DepositEntity> getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId,Integer amountOfMoney);
    DepositEntity getByAccountIdAndId(Integer accountId,Integer id);
}
