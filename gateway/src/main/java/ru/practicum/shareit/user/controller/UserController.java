package ru.practicum.shareit.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.validation.Created;
import ru.practicum.shareit.user.validation.Updated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserClient userClient;

    @Validated(Created.class)
    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto) {
        return userClient.createUser(userDto);
    }

    @Validated(Updated.class)
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDto userDto,
                                             @PathVariable Long userId) {
        return userClient.updateUser(userDto, userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@NotNull @PathVariable Long userId) {
        return userClient.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userClient.deleteUser(userId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUsers() {
        return userClient.getUsers();
    }
}