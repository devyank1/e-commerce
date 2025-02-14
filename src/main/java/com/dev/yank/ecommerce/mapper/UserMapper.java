package com.dev.yank.ecommerce.mapper;

import com.dev.yank.ecommerce.dto.UserDTO;
import com.dev.yank.ecommerce.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);

}
