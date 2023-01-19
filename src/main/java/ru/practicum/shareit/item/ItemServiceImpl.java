package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DuplicatedEmailException;
import ru.practicum.shareit.exceptions.IncorrectParameterException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto getItem(Integer userId, Integer itemId) {
        return itemMapper.itemToDto(itemRepository.getItem(userId, itemId));
    }

    @Override
    public List<ItemDto> getAllItems(Integer userId) {
        return itemRepository.getAllItemsByUserId(userId).stream().
                map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto addItem(Integer userId, ItemDto itemDto) {
        Item item = itemMapper.DtoToItem(itemDto);
       User user = userRepository.get(userId);
       item.setOwner(user);
        return itemMapper.itemToDto(itemRepository.addItem(userId,item));
    }

    @Override
    public Item updateItem(Integer userId, Integer itemId, Item item) {
        User user = userRepository.get(userId);
        if(user == item.getOwner() || user == itemRepository.getItem(userId,itemId).getOwner()) {
            return itemRepository.updateItem(userId, itemId, item);
        }else{
        return null;
        }
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        return itemRepository.searchItem(text).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Integer itemId) {
        itemRepository.deleteItem(itemId);
    }


}
