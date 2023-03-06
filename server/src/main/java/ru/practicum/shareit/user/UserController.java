package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.userDto.UserCreateDto;
import ru.practicum.shareit.user.userDto.UserDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        log.debug("List of all users");
        return  ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserDto> getUser(@PathVariable Long id) {

        log.debug(String.format("User with id = %d", id));
        return ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        log.debug(String.format("User with id = %d deleted", id));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              @PathVariable Long userId) {
        log.debug(String.format("User with id = %d updated", userId));
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserCreateDto userDto) {
        log.debug("User with added");
        return ResponseEntity.ok(userService.addUser(userDto));
    }

}
