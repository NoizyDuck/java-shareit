package ru.practicum.shareit.user.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {
    private Long id;
    private String name;
    @Email
    @NotBlank
    private String email;
}