package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
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
            if(itemMap.get(userId) != null || itemMap.get(userId).getId().equals(itemId)){
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
            if(itemMap.get(userId).getId() == itemId){
            itemMap.remove(userId);

            itemMap.put(userId, item);
            }
        }
        return null;
    }

    @Override
    public Item searchItem(Integer userId, String text) {
        if(itemMap.get(userId) != null){
            if(itemMap.get(userId).getDescription().equals(text)){
                return itemMap.get(userId);
            }
        }
        return null;
    }

    private Integer getId() {
      return ++id;
    }
}
