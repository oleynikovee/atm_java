package com.oleynikovee.atm.mapper;

import com.oleynikovee.atm.model.security.User;
import com.oleynikovee.atm.repo.domain.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserEntity user);

    UserEntity toEntity(User user);

    List<User> toModels(List<UserEntity> users);
}
