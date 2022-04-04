package com.example.iseitest.mapper;

import com.example.iseitest.dto.user.UserOutputDto;
import com.example.iseitest.dto.user.UserRegistrationDto;
import com.example.iseitest.dto.user.UserSignInInputDto;
import com.example.iseitest.entity.User;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class
)
public interface UserMapper {

    User toUser(UserRegistrationDto userRegistrationDto);

    UserOutputDto toUserOutputDto(User user);

    User toUser(UserSignInInputDto inputDto);
}
