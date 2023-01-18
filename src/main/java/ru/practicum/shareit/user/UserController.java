package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.userDto.UserDto;

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
    public List<UserDto> getAllUser(){
    List<UserDto> userList = userService.getUsers();
        log.debug("Список всех пользователей был выдан");
        return userList;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        UserDto userDto = userService.getUser(id);
        log.debug(String.format("Пользователь с id = %d был выдан", id));
        return userDto;
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        log.debug(String.format("Пользователь с id = %d удален", id));
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody @Valid UserDto userDto, @PathVariable Integer userId) {
        UserDto saveUserDto = userService.updateUser(userId,userDto);
        log.debug(String.format("Пользователь с id = %d был обновлен", userId));
        return saveUserDto;
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        UserDto saveUserDto = userService.addUser(userDto);
        log.debug(String.format("Новый пользователь был добавлен. Выданный id = %d", saveUserDto.getId()));
        return saveUserDto;
    }

}
