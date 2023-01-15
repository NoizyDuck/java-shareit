package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item get(Integer userId, Integer itemId);

    List<Item> getAllItems(Integer userId);

    Item addItem(Integer userId, Item item);

    Item updateItem(Integer userId, Integer itemId, Item item);

    Item searchItem(Integer userId, String text);
}
