package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
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
    public ItemDto getItem(Integer itemId) {
        return itemMapper.itemToDto(itemRepository.getItem(itemId));
    }

    @Override
    public List<ItemDto> getAllItems(Integer userId) {
        return itemRepository.getAllItemsByUserId(userId).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto addItem(Integer userId, ItemDto itemDto) {
        Item item = itemMapper.DtoToItem(itemDto);
       User user = userRepository.get(userId);
       item.setOwner(user);
        return itemMapper.itemToDto(itemRepository.addItem(item));
    }

    @Override
    public ItemDto updateItem(Integer userId, Integer itemId, ItemDto itemDto) {
        Item item = itemRepository.getItem(itemId);
        User user = userRepository.get(userId);
        Item newItem = validateBeforeUpdate(user, item, itemDto);

        return itemMapper.itemToDto(itemRepository.updateItem(userId, itemId, newItem));
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        if(text.isEmpty()){
            return new ArrayList<>();
        }
        return itemRepository.searchItem(text).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Integer itemId) {
        itemRepository.deleteItem(itemId);
    }

    private Item validateBeforeUpdate(User user, Item item, ItemDto itemDto){
        if (!user.equals(item.getOwner())) {
            throw new NotFoundException("wrong owner");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable()!= null){
           item.setAvailable(itemDto.getAvailable());
        }

        return item;
        }

}
