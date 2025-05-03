package com.supamenu.backend.users.mappers;


import com.supamenu.backend.auth.dtos.RegisterRequestDto;
import com.supamenu.backend.users.User;
import com.supamenu.backend.users.dtos.UserResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toEntity(RegisterRequestDto userDto);
    UserResponseDto toResponseDto(User user);
}
