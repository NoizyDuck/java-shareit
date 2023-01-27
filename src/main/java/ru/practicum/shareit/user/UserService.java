package ru.practicum.shareit.user;

import ru.practicum.shareit.user.userDto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUser(Integer id);

    void removeUser(Integer id);

    UserDto updateUser(Integer userId, UserDto userDto);

    UserDto addUser(UserDto userDto);
}
