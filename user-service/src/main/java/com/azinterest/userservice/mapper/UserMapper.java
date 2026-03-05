package com.azinterest.userservice.mapper;

import com.azinterest.userservice.dto.response.UserResponseDto;
import com.azinterest.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "keycloakUserId", source = "keycloakUserId")
    @Mapping(target = "onBoardingCompleted", source = "onBoardingCompleted")
    UserResponseDto toUserResponseDto(User user);
}

