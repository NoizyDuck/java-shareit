package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto getItem(Integer userId, Integer itemId);

    List<ItemDto> getAllItems(Integer userId);

    ItemDto addItem(Integer userId, ItemDto itemDto);

    Item updateItem(Integer userId, Integer itemId, Item item);

    List<ItemDto> searchItem(String text);

    void deleteItem(Integer itemId);
}
