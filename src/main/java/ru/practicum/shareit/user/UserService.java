package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Integer id);

    void removeUser(Integer id);

    User updateUser(User user);

    User addUser(User user);
}
