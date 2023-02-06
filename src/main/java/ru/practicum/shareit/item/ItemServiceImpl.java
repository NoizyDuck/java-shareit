package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.spi.LocaleServiceProvider;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService{
    private final ItemMapper itemMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public ItemDto getItem(long id) {
        return itemMapper.itemToDto(itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Item id " + id + " not found")));
    }

    public List<ItemDto> getAllItems(long userId) {
        List<Item> items = itemRepository.findAllByOwner_IdOrderById(userId);
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
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
        if(!item.getOwner().getId().equals(userId)){
            throw new NotFoundException("owner not found");
        }
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

    @Override
    public CommentDto createComment(Long userId, long itemId, CommentDto commentDto) {
        Comment comment = commentMapper.dtoToComment(commentDto);
        User author = userMapper.dtoToUser(userService.getUser(userId));
        Item item = itemMapper.dtoToItem(getItem(itemId));
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreatedTime(LocalDateTime.now());
        return commentMapper.commentToDto(commentRepository.save(comment));
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


