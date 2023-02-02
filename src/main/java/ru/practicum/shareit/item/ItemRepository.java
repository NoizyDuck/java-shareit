package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Arrays;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByOwner(Long userId);
    @Query("select i from Item i where (upper(i.name) like upper(concat('%', ?1, '%')) or " +
            "upper(i.description) like upper(concat('%', ?1, '%'))) and i.available is true ")
    List<Item> findAllByText(String text);

//    Item getItem(Integer itemId);
//
//    List<Item> getAllItemsByUserId(Integer userId);
//
//    Item addItem(Item item);
//
//    Item updateItem(Integer userId, Integer itemId, Item item);
//
//    List<Item> searchItem(String text);
//
//    void deleteItem(Integer itemId);
}
