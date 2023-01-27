package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto getItem(Integer itemId);

    List<ItemDto> getAllItems(Integer userId);

    ItemDto addItem(Integer userId, ItemDto itemDto);

    ItemDto updateItem(Integer userId, Integer itemId, ItemDto itemDto);

    List<ItemDto> searchItem(String text);

    void deleteItem(Integer itemId);
}
