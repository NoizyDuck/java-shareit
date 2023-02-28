package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;
    @Mock
    private ItemService itemService;
    @Mock
    ItemMapper itemMapper;

    @Test
    void getItem_whenInvoked_thenReturnStatusOkAndReturnItemDto() {
        long userId = 0;
        long itemId = 0;
        ItemDto expectedDto = new ItemDto();
        when(itemService.getItem(itemId, userId)).thenReturn(expectedDto);

        ResponseEntity<ItemDto> response = itemController.getItem(userId, itemId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(itemService).getItem(itemId, userId);

    }

    @Test
    void getItems_whenInvoked_thenReturnStatusOkAndReturnItemDtoList() {
        long userId = 0;
        Integer from = 0;
        Integer size = 0;
        List<Item> itemList = List.of(new Item());
        List<ItemDto> expectedList = itemList.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
        when(itemService.getAllItemsByOwnerId(userId, from, size)).thenReturn(expectedList);

        ResponseEntity<List<ItemDto>> response = itemController.getItems(userId, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(itemService).getAllItemsByOwnerId(userId, from, size);
    }

    @Test
    void createItem_whenInvoked_thenReturnStatusOkAndReturnItemDto() {
        long userId = 0;
        long itemId = 0;
        ItemDto itemDto = new ItemDto();
        when(itemService.addItem(itemId, itemDto)).thenReturn(itemDto);

        ResponseEntity<ItemDto> response = itemController.createItem(userId, itemDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDto, response.getBody());
        verify(itemService).addItem(itemId, itemDto);
    }

    @Test
    void updateItem_whenInvoked_thenReturnStatusOkAndReturnItemDto() {
        long userId = 0;
        long itemId = 0;
        ItemDto itemDto = new ItemDto();
        when(itemService.updateItem(userId, itemId, itemDto)).thenReturn(itemDto);

        ResponseEntity<ItemDto> response = itemController.updateItem(userId, itemId, itemDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDto, response.getBody());
        verify(itemService).updateItem(userId, itemId, itemDto);
    }

    @Test
    void getSearchItem_whenInvoked_thenReturnStatusOkAndReturnItemDtoList() {
        String text = "text";
        Integer from = 0;
        Integer size = 0;
        List<Item> itemList = List.of(new Item());
        List<ItemDto> expectedList = itemList.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
        when(itemService.searchItem(text, from, size)).thenReturn(expectedList);

        ResponseEntity<List<ItemDto>> response = itemController.getSearchItem(text, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(itemService).searchItem(text, from, size);
    }

    @Test
    void deleteItem_whenInvoked_thenItemServiceDeleteItemMethodCall() {
        long itemId = 0;
        itemController.deleteItem(itemId);

        verify(itemService).deleteItem(itemId);
    }

    @Test
    void createComment_whenInvoked_thenReturnStatusOkAndReturnCommentDto() {
        long userId = 0;
        long itemId = 0;
        CommentDto commentDto = new CommentDto();
        ReturnCommentDto returnCommentDto = new ReturnCommentDto();
        when(itemService.createComment(userId, itemId, commentDto)).thenReturn(returnCommentDto);

        ResponseEntity<ReturnCommentDto> response = itemController.createComment(userId, itemId, commentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnCommentDto, response.getBody());
        verify(itemService).createComment(userId, itemId, commentDto);
    }
}