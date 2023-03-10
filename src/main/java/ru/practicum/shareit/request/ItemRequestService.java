package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto createRequest(Long userId, CreateItemRequestDto createItemRequestDto);

    List<ItemRequestDto> getItemRequestsListById(Long userId);

    ItemRequestDto getItemRequestById(long requestId, long userId);

    List<ItemRequestDto> getAllPaginal(Long userId, Integer from, Integer size);
}
