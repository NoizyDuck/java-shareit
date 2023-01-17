package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item getItem(Integer userId, Integer itemId);

    List<Item> getAllItemsByUserId(Integer userId);

    Item addItem(Integer userId, Item item);

    Item updateItem(Integer userId, Integer itemId, Item item);

    List<Item> searchItem(String text);
}
