package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll();

    User get(Integer id);

    void remove(Integer id);

    User update(Integer userId, User user);

    User add(User user);

    Optional<User> getByEmail(String email);
}
