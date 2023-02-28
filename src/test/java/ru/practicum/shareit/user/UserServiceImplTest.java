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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    UserCreateDto userCreateDto = new UserCreateDto(1L, "name", "email");
    User user = new User(1L, "name", "email");

    UserDto userDto = userMapper.userToDto(user);

    @Test
    void getUsers_whenGetUsers_returnUsersDtoList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> actualList = userServiceImpl.getUsers();

        assertThat(actualList, hasSize(1));
        assertThat(actualList.get(0).getId(), equalTo(user.getId()));
        assertThat(actualList.get(0).getName(), equalTo(userDto.getName()));
        assertThat(actualList.get(0).getEmail(), equalTo(userDto.getEmail()));
        verify(userRepository).findAll();
    }

    @Test
    void getUser_WhenUserFound_thenReturnUserDto() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto actualUser = userServiceImpl.getUser(userId);

        assertThat(actualUser.getId(), equalTo(user.getId()));
        assertThat(actualUser.getName(), equalTo(user.getName()));
        assertThat(actualUser.getEmail(), equalTo(user.getEmail()));
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
        when(userMapper.createDtoToUser(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        UserDto actualUserDto = userServiceImpl.addUser(userCreateDto);

        assertThat(actualUserDto.getId(), equalTo(user.getId()));
        assertThat(actualUserDto.getName(), equalTo(user.getName()));
        assertThat(actualUserDto.getEmail(), equalTo(user.getEmail()));
        verify(userRepository).save(user);
    }
}