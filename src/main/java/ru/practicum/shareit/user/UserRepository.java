package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User get(Integer id);

    void remove(Integer id);

    User update(User user);

    User add(User user);
}
