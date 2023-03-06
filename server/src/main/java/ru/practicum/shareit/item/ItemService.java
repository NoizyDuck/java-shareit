package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ReturnCommentDto;

import java.util.List;


public interface ItemService {

    ItemDto getItem(long id, long userId);

    List<ItemDto> getAllItemsByOwnerId(long userId, Integer from, Integer size);

    ItemDto addItem(long id, ItemDto itemDto);

    ItemDto updateItem(long userId, long itemId, ItemDto itemDto);

    List<ItemDto> searchItem(String text, Integer from, Integer size);

    void deleteItem(long itemId);

    ReturnCommentDto createComment(Long userId, long itemId, CommentDto comment);
}
