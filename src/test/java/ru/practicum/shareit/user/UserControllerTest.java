package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.userDto.UserCreateDto;
import ru.practicum.shareit.user.userDto.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    void getAllUser_whenInvoked_thenResponseStatusOkWithUsersCollectionBody() {
        List<UserDto> expected = List.of(new UserDto());
        when(userService.getUsers()).thenReturn(expected);

        ResponseEntity<List<UserDto>> response = userController.getAllUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getUserId_whenInvoked_thenResponseStatusOkWithUserDtoBody() {
        long userId = 0L;
        UserDto expectedUserDto = new UserDto();
        when(userService.getUser(userId)).thenReturn(expectedUserDto);

        ResponseEntity<UserDto> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserDto, response.getBody());
    }

    @Test
    void removeUserById() {
        long userId = 0L;
       userController.removeUser(userId);

        verify(userService).removeUser(userId);
    }

    @Test
    void updateUser_whenInvoked_thenResponseStatusOkWithUserDtoBody() {
        long userId = 0L;
        UserDto expectedUserDto = new UserDto();
        when((userService.updateUser(userId, expectedUserDto))).thenReturn(expectedUserDto);

        ResponseEntity<UserDto> response = userController.updateUser(expectedUserDto, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserDto, response.getBody());
    }

    @Test
    void addUser() {
        UserCreateDto userCreateDto = new UserCreateDto();
        UserDto expectedUserDto = new UserDto();
        when(userService.addUser(userCreateDto)).thenReturn(expectedUserDto);

        ResponseEntity<UserDto> response = userController.addUser(userCreateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUserDto, response.getBody());
        verify(userService).addUser(userCreateDto);
    }
}