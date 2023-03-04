package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item dtoToItem(ItemDto itemDto);

    ItemDto itemToDto(Item item);

    @Mapping(target = "ownerId", source = "owner.id")
    ShortItemDto itemToShortDto(Item item);


}
