package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
private final UserService userService;
    @GetMapping
    public List<User> getAllUser(){
    List<User> userList = userService.getUsers();
        log.debug("Список всех пользователей был выдан");
        return userList;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        log.debug(String.format("Пользователь с id = %d был выдан", id));
        return user;
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        log.debug(String.format("Пользователь с id = %d удален", id));
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        User saveUser = userService.updateUser(user);
        log.debug(String.format("Пользователь с id = %d был обновлен", saveUser.getId()));
        return saveUser;
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        User saveUser = userService.addUser(user);
        log.debug(String.format("Новый пользователь был добавлен. Выданный id = %d", saveUser.getId()));
        return saveUser;
    }

}
