package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DuplicatedEmailException;
import ru.practicum.shareit.user.userDto.UserDto;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers() {
        log.debug("Выдача всех пользователей");

        return userRepository.getAll().stream().map(userMapper::userToDto).collect(Collectors.toList());
    }

    public UserDto getUser(Integer id) {
        log.debug(String.format("Выдача пользователя c id = %d", id));
        return userMapper.userToDto(userRepository.get(id));
    }

    public void removeUser(Integer id) {
        log.debug(String.format("Удаление пользователя c id = %d", id));
        userRepository.remove(id);
    }

    public UserDto updateUser(Integer userId, UserDto userDto) {
//        User user = userMapper.DtoToUser(userDto);
        User user = userRepository.get(userId);
        if (userDto.getEmail() != null) {
            EmailDuplicateCheck(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        log.debug(String.format("Обновление пользователя c id = %d", userId));
        return userMapper.userToDto(userRepository.update(userId, user));
    }

    public UserDto addUser(UserDto userDto) {
        User user = userMapper.DtoToUser(userDto);
        log.debug("Добавление пользователя");
        return userMapper.userToDto(userRepository.add(user));
    }

    private void EmailDuplicateCheck(String email) {
        userRepository.getByEmail(email).ifPresent(user -> {
            throw new DuplicatedEmailException(email);
        });
    }
}
