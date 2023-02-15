package com.oleynikovee.atm.mapper;

import com.oleynikovee.atm.model.Deposit;
import com.oleynikovee.atm.repo.domain.DepositEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepositMapper {
    Deposit toModel(DepositEntity deposit);

    DepositEntity toEntity(Deposit deposit);

    List<Deposit> toModels(List<DepositEntity> deposits);

}