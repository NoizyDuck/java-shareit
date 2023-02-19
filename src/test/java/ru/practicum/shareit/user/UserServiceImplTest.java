package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.userDto.UserCreateDto;
import ru.practicum.shareit.user.userDto.UserDto;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
@Mock
private UserRepository userRepository;
@Spy
private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
@InjectMocks
private UserServiceImpl userServiceImpl;
@Captor
private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void getUsers_whenGetUsers_returnUsersDtoList() {
        List<User> userList = List.of(new User());
        List<UserDto> expectedList = userList.stream().map(userMapper::userToDto).collect(Collectors.toList());
        when(userRepository.findAll()).thenReturn(List.of(new User()));

        List<UserDto> actualList = userServiceImpl.getUsers();

        assertEquals(expectedList, actualList);
        verify(userRepository).findAll();
    }

    @Test
    void getUser_WhenUserFound_thenReturnUserDto() {
        long userId = 0L;
        User user = new User();
        UserDto expectedUser = userMapper.userToDto(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto actualUser = userServiceImpl.getUser(userId);

        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUser_WhenUserNotFound_thenNotFoundExceptionThrow() {
        long userId = 0L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userServiceImpl.getUser(userId));
    }

    @Test
    void removeUser() {
            long userId = 1L;
            User user = new User();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            userServiceImpl.removeUser(userId);

            verify(userRepository).delete(user);
    }

    @Test
    void updateUser_whenUserUpdateAllFields_returnUpdatedUserDto() {
        long userId = 0L;
        User oldUser = new User();
        oldUser.setName("name");
        oldUser.setEmail("email@email.ru");

        User newUser = new User();
        newUser.setName("newName");
        newUser.setEmail("new@Email.ru");
        UserDto newUserDto = userMapper.userToDto(newUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(oldUser));

        userServiceImpl.updateUser(userId, newUserDto);

       verify(userRepository).save(userArgumentCaptor.capture());
       User savedUser = userArgumentCaptor.getValue();

        assertEquals("newName", savedUser.getName());
        assertEquals("new@Email.ru", savedUser.getEmail());
    }

    @Test
    void updateUser_whenUserUpdateName_returnUpdatedUserDto() {
        long userId = 0L;
        User oldUser = new User();
        oldUser.setName("name");
        oldUser.setEmail("email@email.ru");

        User newUser = new User();
        newUser.setName("newName");
        UserDto newUserDto = userMapper.userToDto(newUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(oldUser));

        userServiceImpl.updateUser(userId, newUserDto);

        verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        assertEquals("newName", savedUser.getName());
        assertEquals("email@email.ru", savedUser.getEmail());
    }

    @Test
    void updateUser_whenUserUpdateEmail_returnUpdatedUserDto() {
        long userId = 0L;
        User oldUser = new User();
        oldUser.setName("name");
        oldUser.setEmail("email@email.ru");

        User newUser = new User();
        newUser.setEmail("new@Email.ru");
        UserDto newUserDto = userMapper.userToDto(newUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(oldUser));

        userServiceImpl.updateUser(userId, newUserDto);

        verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        assertEquals("name", savedUser.getName());
        assertEquals("new@Email.ru", savedUser.getEmail());
    }

    @Test
    void addUser_whenAddUser_returnUserDto() {
        UserCreateDto userCreateDto = new UserCreateDto();
        User userToSave = userMapper.createDtoToUser(userCreateDto);
        UserDto expectedUserDto = userMapper.userToDto(userToSave);
        when(userRepository.save(userToSave)).thenReturn(userToSave);

        UserDto actualUserDto = userServiceImpl.addUser(userCreateDto);

        assertEquals(expectedUserDto, actualUserDto);
        verify(userRepository).save(userToSave);
    }
}