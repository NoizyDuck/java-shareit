package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DuplicatedEmailException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.userDto.UserDto;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.util.List;
import java.util.stream.Collectors;


public interface UserService {

    public List<UserDto> getUsers();

    public UserDto getUser(long id);

    public void removeUser(long id);

    public UserDto updateUser(long id, UserDto userDto);

    public UserDto addUser(UserDto userDto);

//    private void emailDuplicateCheck(String email);
}
