package ru.practicum.shareit.user.userDto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.User;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);

    User createDtoToUser (UserCreateDto createDto);
    UserCreateDto userToCreateDto(User user);

}

