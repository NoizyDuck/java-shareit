package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.exceptions.IncorrectParameterException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.userDto.UserDto;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    CommentRepository commentRepository;
    @Spy
    private BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Spy
    private ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);
    @Mock
    UserService userService;
    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    User user = new User(1L, "name", "email");
    Item item = new Item(1L, "itemName", "itemDescription", true, user, 1L);

    @Test
    void getItem_whenInvoked_returnItemDto() {
        long itemId = 1;
        long userId = 1;
        ItemDto expectedDto = itemMapper.itemToDto(item);
        expectedDto.setComments(Collections.emptyList());
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        ItemDto actualDto = itemService.getItem(itemId, userId);

        assertEquals(expectedDto, actualDto);
        verify(itemRepository).findById(itemId);

    }

    @Test
    void getItem_whenItemNotFound_thenThrowNotFoundException() {
        long itemId = 1;
        long userId = 1;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.getItem(itemId, userId));

    }

    @Test
    void getAllItemsByOwnerId_whenInvoked_thenReturnListItemDto() {
        long userId = 0;
        Integer from = 1;
        Integer size = 1;
        ItemDto itemDto = itemMapper.itemToDto(item);
        itemDto.setComments(Collections.emptyList());
        List<ItemDto> expectedList = List.of(itemDto);
        when(itemRepository.findAllByOwner_IdOrderById(anyLong(), any())).thenReturn(List.of(item));
        when(commentRepository.findAllByItem_Id(any())).thenReturn(Collections.emptyList());

        List<ItemDto> actualList = itemService.getAllItemsByOwnerId(userId, from, size);

        assertEquals(expectedList, actualList);
        verify(itemRepository).findAllByOwner_IdOrderById(anyLong(), any());

    }

    @Test
    void addItem_whenInvoked_thenReturnItemDto() {
        long userId = 1;
        ItemDto itemDto = itemMapper.itemToDto(item);
        when(itemRepository.save(item)).thenReturn(item);

        ItemDto actualDto = itemService.addItem(userId, itemDto);

        assertEquals(itemDto, actualDto);
        verify(itemRepository).save(item);
    }

    @Test
    void updateItem_whenUserIdNotOwnerID_thenThrowNotFoundException() {
        long userId = 0;
        long itemId = 0;
        ItemDto itemDto = itemMapper.itemToDto(item);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        assertThrows(NotFoundException.class, () -> itemService.updateItem(itemId, userId, itemDto));

    }

    @Test
    void updateItem_whenItemNotFound_thenThrowNotFoundException() {
        long userId = 0;
        long itemId = 0;
        ItemDto itemDto = itemMapper.itemToDto(item);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemService.updateItem(itemId, userId, itemDto));

    }

    @Test
    void updateItem_whenInvoked_itemUpdated() {
        long userId = 1;
        long itemId = 1;
        Item newItem = new Item(2L, "newName", "newDescription", false, user, null);
        ItemDto itemDto = itemMapper.itemToDto(newItem);
        UserDto userDto = userMapper.userToDto(user);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(userService.getUser(userId)).thenReturn(userDto);

        itemService.updateItem(userId, itemId, itemDto);

        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item savedItem = itemArgumentCaptor.getValue();

        assertEquals(1, savedItem.getId());
        assertEquals("newName", savedItem.getName());
        assertEquals("newDescription", savedItem.getDescription());
        assertFalse(savedItem.isAvailable());
    }

    @Test
    void searchItem_whenInvoked_thenReturnDtoList() {
        String text = "text";
        int from = 1;
        int size = 1;
        List<ItemDto> expectedDtoList = Stream.of(new Item()).map(itemMapper::itemToDto).collect(Collectors.toList());
        when(itemRepository.findAllByText(anyString(), any())).thenReturn(List.of(new Item()));

        List<ItemDto> actualDtoList = itemService.searchItem(text, from, size);

        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void searchItem_whenTextIsEmpty_thenReturnEmptyList() {
        String text = "";
        int from = 1;
        int size = 1;
        List<ItemDto> expectedDtoList = new ArrayList<>();

        List<ItemDto> actualDtoList = itemService.searchItem(text, from, size);

        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void deleteItem_whenInvoked_thenDeleteByIdCalled() {
        long itemId = 0;

        itemService.deleteItem(itemId);

        verify(itemRepository).deleteById(itemId);
    }

    @Test
    void createComment_whenInvoked_thenReturnCommentDto() {
        long userId = 1L;
        long itemId = 1L;
        CommentDto commentDto = new CommentDto("text");
        Comment comment = new Comment(1L, "text", item, user, LocalDateTime.now());
        ReturnCommentDto returnCommentDto = commentMapper.returnDtoToComment(comment);
        List<Booking> bookings = List.of(new Booking());
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(anyLong(), any())).thenReturn(bookings);
        when(commentRepository.save(any())).thenReturn(comment);

        ReturnCommentDto actualDto = itemService.createComment(userId, itemId, commentDto);

        assertEquals(returnCommentDto, actualDto);
    }

    @Test
    void createComment_whenBookingListIsEmpty_thenThrowIncorrectParameterException() {
        long userId = 1L;
        long itemId = 1L;
        CommentDto commentDto = new CommentDto("text");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(anyLong(), any())).thenReturn(Collections.emptyList());

        assertThrows(IncorrectParameterException.class, () -> itemService.createComment(userId, itemId, commentDto));

    }
}