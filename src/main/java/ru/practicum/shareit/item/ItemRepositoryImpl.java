package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.*;
@Component
public class ItemRepositoryImpl implements ItemRepository{

    private final Map<Integer,Item> itemMap = new HashMap<>();
    private Integer id = 0;
    @Override
    public Item get(Integer userId, Integer itemId) {
        if(itemMap.containsKey(userId)){
            if(itemMap.get(userId).getId().equals(itemId)){
                return itemMap.get(userId);
            }
        }
             return null;
    }

    @Override
    public List<Item> getAllItems(Integer userId) {
        return null;
    }

    @Override
    public Item addItem(Integer userId, Item item) {
        item.setId(getId());
        itemMap.put(userId,item);
        return item;
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, Item item) {
        if (itemMap.containsKey(userId)){
            if(Objects.equals(itemMap.get(userId).getId(), itemId)){
            itemMap.remove(userId);
            itemMap.put(userId, item);
            }
        }
        return item;
    }

    @Override
    public List<Item> searchItem(String text) {
        List<Item> itemsList = new ArrayList<>();
        for (Item item: itemMap.values()) {
            if(item.getDescription().matches(text)){
                itemsList.add(item);
        }

            }return itemsList;
        }

private void validateItemId(Integer id){
        if(id != 0 && !itemMap.containsKey(id)){
            throw new NotFoundException("Item with id " +id + " not found");
        }
}

    private Integer getId() {
      return ++id;
    }

}
