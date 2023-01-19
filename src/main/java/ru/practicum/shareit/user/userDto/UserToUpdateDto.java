package ru.practicum.shareit.user.userDto;

import lombok.Value;

import javax.validation.constraints.Email;
@Value
public class UserToUpdateDto {
    String name;
    @Email(message = "Email is incorrect")
    String email;
}
