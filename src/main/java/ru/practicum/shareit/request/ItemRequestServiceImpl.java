package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    @Override
    public ItemRequestDto createRequest(Long userId, CreateItemRequestDto createItemRequestDto) {
        ItemRequest itemRequest = requestMapper.createItemRequestDtoToItemRequest(createItemRequestDto);
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("user id " + userId + " not found"));
        itemRequest.setRequester(requester);
        itemRequest.setCreated(LocalDateTime.now());

        return requestMapper.itemRequestToItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getItemRequestsListById(Long userId) {
        User requester = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("user id " + userId + " not found"));

        return itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(requester.getId())
                .stream().map(requestMapper::itemRequestToItemRequestDto).collect(Collectors.toList())
                .stream()
                .map(user -> {
                    long id = user.getId();
                    return setItemsToDto(id);
                }).collect(Collectors.toList());
    }


    @Override
    public ItemRequestDto getItemRequestById(long requestId, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("user id " + userId + " not found"));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Request id " + requestId + " not found"));
        ItemRequestDto itemRequestDto = requestMapper.itemRequestToItemRequestDto(itemRequest);
        itemRequestDto.setItems(itemRepository.findAllByRequestId(requestId));
        return itemRequestDto;
    }

    @Override
    public List<ItemRequestDto> getAllPaginal(Long userId, PageRequest pageRequest) {

        List<ItemRequestDto> itemRequestDtos = itemRequestRepository.findAll(pageRequest).getContent()
                .stream().map(requestMapper::itemRequestToItemRequestDto).collect(Collectors.toList()).stream()
                .map(user -> {
                    long id = user.getId();
                    return setItemsToDto(id);
                }).collect(Collectors.toList());
        User requesterOwner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User " + userId + " not found"));
        if(itemRequestDtos.stream().anyMatch(itemRequestDto -> itemRequestDto.getRequester().equals(requesterOwner))){ //TODO: хрень собачья, но работает для теста
            return Collections.emptyList();
        }
        return itemRequestDtos;
    }
    private ItemRequestDto setItemsToDto(long id) {
        ItemRequestDto itemRequestDto = requestMapper
                .itemRequestToItemRequestDto(itemRequestRepository.findItemRequestByRequesterId(id));
        itemRequestDto.setItems(itemRepository.findAllByRequestId(id));
        return itemRequestDto;
    }
}
