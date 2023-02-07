package ru.practicum.shareit.user;

import ru.practicum.shareit.user.userDto.UserCreateDto;
import ru.practicum.shareit.user.userDto.UserDto;

import java.util.List;


public interface UserService {

    List<UserDto> getUsers();

    UserDto getUser(long id);

    void removeUser(long id);

    UserDto updateUser(long id, UserDto userDto);

    UserDto addUser(UserCreateDto userDto);

}
