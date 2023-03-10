package ru.practicum.shareit.item;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where (upper(i.name) like upper(concat('%', ?1, '%')) or " +
            "upper(i.description) like upper(concat('%', ?1, '%'))) and i.available is true ")
    List<Item> findAllByText(String text, PageRequest pageRequest);

    List<Item> findAllByOwner_IdOrderById(Long ownerId, PageRequest pageRequest);

    List<Item> findAllByRequestId(Long requestId);
}
