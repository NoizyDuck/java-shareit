package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
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
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @PathVariable Integer itemId){

        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Integer userId){
        return itemService.getAllItems(userId);
    }

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                    @RequestBody ItemDto itemDto){
        return itemService.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @PathVariable Integer itemId,
                           @RequestBody Item item){
        return itemService.updateItem(userId, itemId, item);
    }

    @GetMapping("/search")
        public List<ItemDto> getSearchItem(@RequestParam(name = "text") String text){
        return itemService.searchItem(text);
        }

        @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Integer id){
        itemService.deleteItem(id);
        log.debug(String.format("Item with id /%d was deleted", id));
        }

}
