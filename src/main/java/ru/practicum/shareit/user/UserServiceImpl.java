package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public List<User> getUsers() {
        log.debug("Выдача всех пользователей");
        return userRepository.getAll();
    }


    public User getUser(Integer id) {
        log.debug(String.format("Выдача пользователя c id = %d", id));
        return userRepository.get(id);
    }

    public void removeUser(Integer id) {
        log.debug(String.format("Удаление пользователя c id = %d", id));
        userRepository.remove(id);
    }

    public User updateUser(User user) {
        long id = user.getId();
        log.debug(String.format("Обновление пользователя c id = %d", id));
        return userRepository.update(user);
    }

    public User addUser(User user) {
        log.debug("Добавление пользователя");
        return userRepository.add(user);
    }
}
