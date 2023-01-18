package ru.practicum.shareit.user.userDto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.User;
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUserDto(UserDto userDto);
    }

