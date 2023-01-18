package ru.practicum.shareit.user.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Integer id;
    @NotBlank
    private String name;
    @Email
    private String email;
}
