package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {
    @InjectMocks
    private ItemRequestController itemRequestController;
    @Mock
    private ItemRequestService itemRequestService;

    @Test
    void createRequest() {
        long userId = 0;
        CreateItemRequestDto createItemRequestDto = new CreateItemRequestDto();
        ItemRequestDto expectedDto = new ItemRequestDto();
        when(itemRequestService.createRequest(userId, createItemRequestDto)).thenReturn(expectedDto);

        ResponseEntity<ItemRequestDto> response = itemRequestController.createRequest(userId, createItemRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(itemRequestService).createRequest(userId, createItemRequestDto);
    }

    @Test
    void getItemRequestListById() {
        long userId = 0;
        List<ItemRequestDto> expectedList = List.of(new ItemRequestDto());
        when(itemRequestService.getItemRequestsListById(userId)).thenReturn(expectedList);

        ResponseEntity<List<ItemRequestDto>> response = itemRequestController.getItemRequestListById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(itemRequestService).getItemRequestsListById(userId);
    }

    @Test
    void getRequestById() {
        long responseId = 0;
        long userId = 0;
        ItemRequestDto expectedDto = new ItemRequestDto();
        when(itemRequestService.getItemRequestById(responseId, userId)).thenReturn(expectedDto);

        ResponseEntity<ItemRequestDto> response = itemRequestController.getRequestById(responseId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(itemRequestService).getItemRequestById(responseId, userId);
    }

    @Test
    void getItemRequestPagination() {
        long userId = 0;
        Integer from = 0;
        Integer size = 0;
        List<ItemRequestDto> expectedList = List.of(new ItemRequestDto());
        when(itemRequestService.getAllPaginal(userId, from, size)).thenReturn(expectedList);

        ResponseEntity<List<ItemRequestDto>> response = itemRequestController.getItemRequestPagination(userId, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        verify(itemRequestService).getAllPaginal(userId, from, size);
    }
}