package com.oleynikovee.atm.mapper;


import com.oleynikovee.atm.model.Account;
import com.oleynikovee.atm.repo.domain.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toModel(AccountEntity account);

    AccountEntity toEntity(Account account);

    List<Account> toModels(List<AccountEntity> account);
}
