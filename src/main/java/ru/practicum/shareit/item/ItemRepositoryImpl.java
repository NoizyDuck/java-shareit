package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {


    private final Map<Integer, Item> items = new HashMap<>();
    private Integer id = 0;

    @Override
    public Item getItem(Integer itemId) {

        if (items.get(itemId) == null) {
            throw new NotFoundException("item not found");
        }
        return items.get(itemId);
    }
    @Override
    public List<Item> getAllItemsByUserId(Integer userId) {
        return items.values()
                .stream()
                .filter(item -> Objects.equals(item.getOwner().getId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public Item addItem(Item item) {
        int id = getId();
        item.setId(id);
        items.put(id, item);
        return item;
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, Item item) {
            items.put(itemId, item);
        return item;
    }

    @Override
    public List<Item> searchItem(String text) {
        List<Item> itemsList = new ArrayList<>();
        for (Item item : items.values()) {
            if(item.isAvailable() &&
                    item.getDescription().toLowerCase().contains(text.toLowerCase()) ||
                    item.getName().toLowerCase().contains(text.toLowerCase())){
                itemsList.add(item);
            }
        }
        return itemsList;
    }

    @Override
    public void deleteItem(Integer itemId) {
      items.values().removeIf(item -> item.getId().equals(itemId));
    }


    private Integer getId() {
        return ++id;
    }



}
