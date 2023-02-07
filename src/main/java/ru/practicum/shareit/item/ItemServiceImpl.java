package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.exceptions.IncorrectParameterException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.*;
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

public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;

    public ItemDto getItem(long id, long userId) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Item id " + id + " not found"));
        ItemDto itemDto = itemMapper.itemToDto(item);
        if (item.getOwner().getId() == userId) {
            itemDto.setLastBooking(bookingMapper.bookingToShortBookingDto(
                bookingRepository.findByItem_IdAndEndBeforeOrderByStartDesc(id, LocalDateTime.now())));
        itemDto.setNextBooking(bookingMapper.bookingToShortBookingDto(
                bookingRepository.findByItem_IdAndStartAfterOrderByStartDesc(id, LocalDateTime.now())));
        }
        itemDto.setComments(commentRepository.findAllByItem_Id(id).stream().map(commentMapper::returnDtoToComment).collect(Collectors.toList()));
        return itemDto;
    }

    public List<ItemDto> getAllItems(long userId) {
        List<ItemDto> items = itemRepository.findAllByOwner_IdOrderById(userId).stream().map(itemMapper::itemToDto).
                collect(Collectors.toList())
                .stream()
                .map(item -> { long id = item.getId();
                    return getItemDto(id, item);
                }).collect(Collectors.toList());
        return items;
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
        if (!item.getOwner().getId().equals(userId)) {
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
    public ReturnCommentDto createComment(Long userId, long itemId, CommentDto commentDto) {
        Comment comment = commentMapper.dtoToComment(commentDto);
        User author = userMapper.dtoToUser(userService.getUser(userId));
        Item item = itemMapper.dtoToItem(getItem(itemId, userId));
        List<Booking> bookings = bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
        if (bookings.isEmpty()) {
            throw new IncorrectParameterException("Can't leve comment without booking");
        }
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreatedTime(LocalDateTime.now());
        return commentMapper.returnDtoToComment(commentRepository.save(comment));
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
    private ItemDto getItemDto(long id, ItemDto itemDto) {
        itemDto.setComments(commentRepository.findAllByItem_Id(id).stream().map(commentMapper::returnDtoToComment).collect(Collectors.toList()));
        itemDto.setLastBooking(bookingMapper.bookingToShortBookingDto(
                bookingRepository.findByItem_IdAndEndBeforeOrderByStartDesc(id, LocalDateTime.now())));
        itemDto.setNextBooking(bookingMapper.bookingToShortBookingDto(
                bookingRepository.findByItem_IdAndStartAfterOrderByStartDesc(id, LocalDateTime.now())));
        return itemDto;
    }
}


