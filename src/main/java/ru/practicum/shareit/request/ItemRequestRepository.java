package ru.practicum.shareit.request;

import org.h2.mvstore.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequesterIdOrderByCreatedDesc(Long requester_id);
    @Query("select i from ItemRequest i  ORDER BY created_time")
    List<ItemRequest> findAllByPageRequestOrdOrderByCreated (PageRequest pageRequest);
    ItemRequest findItemRequestByRequesterId(Long requester_id);
}
