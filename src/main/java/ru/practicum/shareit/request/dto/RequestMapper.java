package ru.practicum.shareit.request.dto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.request.ItemRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    ItemRequest createItemRequestDtoToItemRequest(CreateItemRequestDto createItemRequestDto);

    ItemRequestDto itemRequestToItemRequestDto(ItemRequest itemRequest);
}
