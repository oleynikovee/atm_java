package com.oleynikovee.atm.repo.jpa;

import com.oleynikovee.atm.repo.domain.WithdrawEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface WithdrawRepository extends CrudRepository<WithdrawEntity, Integer> {
    boolean existsById(int id);
    List<WithdrawEntity> getAllByAccountId(Integer accountId);
    List<WithdrawEntity> getAllByAccountIdAndAmountOfMoneyGreaterThan(Integer accountId,Integer amountOfMoney);
    List<WithdrawEntity>  getAllByAccountIdAndAmountOfMoneyLessThan(Integer accountId,Integer amountOfMoney);
    WithdrawEntity getByAccountIdAndId(Integer accountId,Integer id);
}
