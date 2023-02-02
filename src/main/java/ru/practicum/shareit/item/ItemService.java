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
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper itemMapper;

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public ItemDto getItem(long id) {
        return itemMapper.itemToDto(itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Item id " + id + " not found")));
    }

    public List<ItemDto> getAllItems(long userId) {
        return itemRepository.findByOwner(userId).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public ItemDto addItem(long id, ItemDto itemDto) {
        Item item = itemMapper.dtoToItem(itemDto);
        User user = userMapper.dtoToUser(userService.getUser(id));
        item.setOwner(user);
        return itemMapper.itemToDto(itemRepository.save(item));
    }

    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException("Item id " + itemId + " not found"));
        User user = userMapper.dtoToUser(userService.getUser(userId));
        item.setOwner(user);
        Item newItem = validateBeforeUpdate(user, item, itemDto);

        return itemMapper.itemToDto(itemRepository.save(newItem));
    }

    public List<ItemDto> searchItem(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.findAllByText(text).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public void deleteItem(long itemId) {
        itemRepository.deleteById(itemId);
    }

    private Item validateBeforeUpdate(User user, Item item, ItemDto itemDto) {
        if (!user.equals(item.getOwner())) {
            throw new NotFoundException("wrong owner");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }

        return item;
    }

}
