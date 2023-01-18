package ru.practicum.shareit.user;

import ru.practicum.shareit.user.userDto.UserDto;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Integer id);

    void removeUser(Integer id);

    User updateUser(Integer userId, User user);

    User addUser(UserDto userDto);
}
