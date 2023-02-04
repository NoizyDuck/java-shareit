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


public interface ItemService {

    public ItemDto getItem(long id);

    public List<ItemDto> getAllItems(long userId);

    public ItemDto addItem(long id, ItemDto itemDto);

    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto);

    public List<ItemDto> searchItem(String text);

    public void deleteItem(long itemId);


}
