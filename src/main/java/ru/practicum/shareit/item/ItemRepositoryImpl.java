package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.NotValidException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository {


    private final Map<Integer, Item> items = new HashMap<>();
    private Integer id = 0;

    @Override
    public Item getItem(Integer userId, Integer itemId) {
        userIdValidation(userId);
        if (items.get(userId).getId().equals(itemId)) {
            return items.get(userId);
        }
        return null;
    }
    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }
    @Override
    public List<Item> getAllItemsByUserId(Integer userId) {
        userIdValidation(userId);
        return items.values()
                .stream()
                .filter(item -> item.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Item addItem(Integer userId, Item item) {
        item.setId(getId());
        items.put(userId, item);
        return item;
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, Item item) {
        userIdValidation(userId);
        if (Objects.equals(items.get(userId).getId(), itemId)) {
            items.remove(userId);
            items.put(userId, item);
        }
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


    private void userIdValidation(Integer userId) {
        if (!items.containsKey(userId)) {
            throw new NotFoundException("User with id " + userId + " not found");
        }
    }
    private void itemIdValidator(Item item) {
        if (!getAllItems().contains(item)) {
            throw new NotFoundException("Item with id " + item.getId() + "not found");
        }
        if (item.getName().isBlank()) {
            throw new NotValidException("Name cant be blank");
        }
        if (item.getDescription().isBlank()) {
            throw new NotValidException("Description cant be blank");
        }
    }
    private Integer getId() {
        return ++id;
    }


}
