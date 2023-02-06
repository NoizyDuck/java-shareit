package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DuplicatedEmailException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.userDto.UserCreateDto;
import ru.practicum.shareit.user.userDto.UserDto;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        log.debug("Users extradition");

        return userRepository.findAll().stream().map(userMapper::userToDto).collect(Collectors.toList());
    }

    public UserDto getUser(long id) {
        log.debug(String.format("Extradition user with id = %d", id));
        return userMapper.userToDto(userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("user id " + id + " not found")));
    }

    public void removeUser(long id) {
        log.debug(String.format("Deleting user with id = %d", id));
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("user id " + id + " not found"));
        userRepository.delete(user);
    }

    public UserDto updateUser(long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("user id " + id + " not found"));
        if (userDto.getEmail() != null) {
            emailDuplicateCheck(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        log.debug(String.format("Updating user with id = %d", id));
        return userMapper.userToDto(userRepository.save(user));
    }

    public UserDto addUser(UserCreateDto userDto) {
        User user = userMapper.createDtoToUser(userDto);
        log.debug("Adding user");
        return userMapper.userToDto(userRepository.save(user));
    }

    private void emailDuplicateCheck(String email) {
        userRepository.getByEmail(email).ifPresent(user -> {
            throw new DuplicatedEmailException(email);
        });
    }
}


