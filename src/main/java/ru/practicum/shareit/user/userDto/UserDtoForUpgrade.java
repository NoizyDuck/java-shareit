package ru.practicum.shareit.user.userDto;

import lombok.Value;

import javax.validation.constraints.Email;

@Value
public class UserDtoForUpgrade{
    String name;
    @Email
    String email;
}
