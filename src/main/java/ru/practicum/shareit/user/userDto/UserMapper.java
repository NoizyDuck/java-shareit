package ru.practicum.shareit.user.userDto;

import ru.practicum.shareit.user.User;

public class UserMapper {

    public static UserDto toUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
