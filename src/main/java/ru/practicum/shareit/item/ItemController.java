package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ReturnCommentDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable Long itemId) {
        log.debug(String.format("Item with id /%d", itemId));
        return ResponseEntity.ok(itemService.getItem(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @RequestParam(required = false) Integer from,
                                  @RequestParam(required = false) Integer size) {
        log.debug(String.format("Items of user with id /%d ", userId));
        return ResponseEntity.ok(itemService.getAllItemsByOwnerId(userId, from, size));
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody @Valid ItemDto itemDto) {
        log.debug("Item created");
        return ResponseEntity.ok(itemService.addItem(userId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @PathVariable Long itemId,
                              @RequestBody ItemDto itemDto) {
        log.debug("Item updated");
        return ResponseEntity.ok(itemService.updateItem(userId, itemId, itemDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> getSearchItem(@RequestParam(name = "text") String text,
                                       @RequestParam(required = false) Integer from,
                                       @RequestParam(required = false) Integer size) {
        log.debug("Result of searching request");
        return ResponseEntity.ok(itemService.searchItem(text, from, size));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        log.debug(String.format("Item with id /%d was deleted", id));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<ReturnCommentDto> createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @PathVariable long itemId,
                                          @RequestBody @Valid CommentDto comment) {
        log.debug("Comment creation");
        return ResponseEntity.ok(itemService.createComment(userId, itemId, comment));
    }


}
