package com.oleynikovee.atm.mapper;

import com.oleynikovee.atm.model.Withdraw;
import com.oleynikovee.atm.repo.domain.WithdrawEntity;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface WithdrawMapper {
    Withdraw toModel(WithdrawEntity withdraw);

    WithdrawEntity toEntity(Withdraw withdraw);

    List<Withdraw> toModels(List<WithdrawEntity> withdraws);

}
