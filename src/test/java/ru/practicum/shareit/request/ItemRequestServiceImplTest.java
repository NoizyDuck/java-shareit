package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;
    @Spy
    private RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemRequestRepository itemRequestRepository;
    User user = new User(1L, "name", "email");
    Item item = new Item(1L, "itemName", "itemDescription", true, user, 1L);
    CreateItemRequestDto createItemRequestDto = new CreateItemRequestDto("description");
    ItemRequest itemRequest = new ItemRequest(1L, "description", user, LocalDateTime.now());

    @Test
    void createRequest_whenInvoked_thenReturnItemRequestDto() {
        long userId = 1;
        ItemRequestDto expectedDto = requestMapper.itemRequestToItemRequestDto(itemRequest);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.save(any())).thenReturn(itemRequest);

        ItemRequestDto actualDto = itemRequestService.createRequest(userId, createItemRequestDto);

        assertEquals(expectedDto, actualDto);

    }

    @Test
    void createRequest_whenUserNotFound_thenThrowNotFoundException() {
        long userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemRequestService.createRequest(userId, createItemRequestDto));
    }

    @Test
    void getItemRequestsListById_whenInvoked_thenReturnItemRequestDtoList() {
        long userId = 1;
        ItemRequestDto itemRequestDto = requestMapper.itemRequestToItemRequestDto(itemRequest);
        itemRequestDto.setItems(Collections.emptyList());
        List<ItemRequest> itemRequestList = List.of(itemRequest);
        List<ItemRequestDto> expectedList = List.of(itemRequestDto);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.findItemRequestByRequesterId(userId)).thenReturn(itemRequest);
        when(itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId)).thenReturn(itemRequestList);
        when(itemRepository.findAllByRequestId(userId)).thenReturn(anyList());

        List<ItemRequestDto> actualList = itemRequestService.getItemRequestsListById(userId);

        assertEquals(expectedList, actualList);

    }

    @Test
    void getItemRequestsListById_whenUserNotFound_thenThrowNotFoundException() {
        long userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemRequestService.getItemRequestsListById(userId));

    }

    @Test
    void getItemRequestById_whenInvoked_thenReturnItemRequestDto() {
        long requestId = 1;
        long userId = 1;
        ItemRequestDto expectedDto = requestMapper.itemRequestToItemRequestDto(itemRequest);
        expectedDto.setItems(List.of(item));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(requestId)).thenReturn(Optional.of(itemRequest));
        when(itemRepository.findAllByRequestId(userId)).thenReturn(List.of(item));

        ItemRequestDto actualDto = itemRequestService.getItemRequestById(requestId, userId);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void getItemRequestById_whenUserNotFound_thenThrowNotFoundException() {
        long requestId = 1;
        long userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemRequestService.getItemRequestById(requestId, userId));
    }

    @Test
    void getItemRequestById_whenItemNotFound_thenThrowNotFoundException() {
        long requestId = 1;
        long userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> itemRequestService.getItemRequestById(requestId, userId));
    }

    @Test
    void getAllPaginal() {
    }
}