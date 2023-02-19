package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    ItemRequestDto createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestBody @Valid CreateItemRequestDto createItemRequestDto) {
        log.debug("Creating a new ItemRequest");
        return itemRequestService.createRequest(userId, createItemRequestDto);
    }

    @GetMapping
    List<ItemRequestDto> getItemRequestListById(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.debug("Items requests for user id " + userId);
        return itemRequestService.getItemRequestsListById(userId);
    }

    @GetMapping("/{requestId}")
    ItemRequestDto getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @PathVariable long requestId) {
        log.debug("Item request id" + requestId);
        return itemRequestService.getItemRequestById(requestId, userId);
    }

    @GetMapping("/all")
    List<ItemRequestDto> getItemRequestPaginal(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(required = false, defaultValue = "0") int from,
                                               @RequestParam(required = false, defaultValue = "1") int size) {
        return itemRequestService.getAllPaginal(userId, PageRequest.of(from, size));
    }


}
