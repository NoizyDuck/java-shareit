package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    ResponseEntity<ItemRequestDto> createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @RequestBody CreateItemRequestDto createItemRequestDto) {
        log.debug("Creating a new ItemRequest");
        return ResponseEntity.ok(itemRequestService.createRequest(userId, createItemRequestDto));
    }

    @GetMapping
    ResponseEntity<List<ItemRequestDto>> getItemRequestListById(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.debug("Items requests for user id " + userId);
        return ResponseEntity.ok(itemRequestService.getItemRequestsListById(userId));
    }

    @GetMapping("/{requestId}")
    ResponseEntity<ItemRequestDto> getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @PathVariable long requestId) {
        log.debug("Item request id" + requestId);
        return ResponseEntity.ok(itemRequestService.getItemRequestById(requestId, userId));
    }

    @GetMapping("/all")
    ResponseEntity<List<ItemRequestDto>>
    getItemRequestPagination(@RequestHeader("X-Sharer-User-Id")
                          Long userId, @RequestParam(required = false, defaultValue = "0") int from,
                          @RequestParam(required = false, defaultValue = "1") int size) {
        return ResponseEntity.ok(itemRequestService.getAllPaginal(userId, from, size));
    }


}
