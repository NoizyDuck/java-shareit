package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public Item getItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                        @PathVariable Integer itemId){

        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<Item> getItems(@RequestHeader("X-Sharer-User-Id") Integer userId){
        return itemService.getAllItems(userId);
    }

    @PostMapping
    public Item createItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                    @RequestBody Item item){
        return itemService.addItem(userId, item);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @PathVariable Integer itemId,
                           @RequestBody Item item){
        return itemService.updateItem(userId, itemId, item);
    }

    @GetMapping("/search?text={text}")
        public Item getSearchItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                  @PathVariable String text){
        return itemService.searchItem(userId, text);
        }
}
