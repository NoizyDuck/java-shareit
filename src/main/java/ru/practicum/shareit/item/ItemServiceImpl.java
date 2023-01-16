package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item getItem(Integer userId, Integer itemId) {

        return itemRepository.get(userId, itemId);
    }

    @Override
    public List<Item> getAllItems(Integer userId) {
        return itemRepository.getAllItems(userId);
    }

    @Override
    public Item addItem(Integer userId, Item item) {
       User user = userRepository.get(userId);
       item.setOwner(user);
        return itemRepository.addItem(userId,item);
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, Item item) {
        User user = userRepository.get(userId);
        if(user == item.getOwner() || user == itemRepository.get(userId,itemId).getOwner()) {
            return itemRepository.updateItem(userId, itemId, item);
        }else{
        return null;
        }
    }

    @Override
    public List<Item> searchItem(String text) {
        return itemRepository.searchItem(text);
    }
}
