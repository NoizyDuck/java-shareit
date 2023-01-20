package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.userDto.UserDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
private final UserService userService;
    @GetMapping
    public List<UserDto> getAllUser(){
    List<UserDto> userList = userService.getUsers();
        log.debug("List of all users");
        return userList;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        UserDto userDto = userService.getUser(id);
        log.debug(String.format("User with id = %d", id));
        return userDto;
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        log.debug(String.format("User with id = %d deleted", id));
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody @Valid UserDto userDto, @PathVariable Integer userId) {
        UserDto saveUserDto = userService.updateUser(userId,userDto);
        log.debug(String.format("User with id = %d updated", userId));
        return saveUserDto;
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        UserDto saveUserDto = userService.addUser(userDto);
        log.debug(String.format("User with id = %d added", saveUserDto.getId()));
        return saveUserDto;
    }

}
