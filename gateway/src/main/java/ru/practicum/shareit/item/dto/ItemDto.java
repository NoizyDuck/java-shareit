package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.validation.Created;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;
    @NotBlank(groups = Created.class, message = "Item name cannot be null")
    private String name;
    @NotBlank(groups = Created.class, message = "Item description cannot be null")
    private String description;
    @NotNull(groups = Created.class, message = "Available cannot be null")
    private Boolean available;
    private Long ownerId;
    private Long requestId;
}
