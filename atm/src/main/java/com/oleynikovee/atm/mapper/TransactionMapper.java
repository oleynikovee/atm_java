package com.oleynikovee.atm.mapper;

import com.oleynikovee.atm.model.Transaction;
import com.oleynikovee.atm.repo.domain.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toModel(TransactionEntity transaction);

    TransactionEntity toEntity(Transaction transaction);

    List<Transaction> toModels(List<TransactionEntity> transactions);
}
