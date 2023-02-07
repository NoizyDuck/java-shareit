package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findAllByBooker_IdOrderByStartDesc(Long bookerId);

    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByBooker_IdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);

    List<Booking> findAllByBooker_IdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime start);

    List<Booking> findAllByBooker_IdAndStatusOrderByStart(Long bookerId, BookingStatus status);

    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(Long itemOwnerId);

    List<Booking> findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long item_owner_id, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(Long itemOwnerId, LocalDateTime end);

    List<Booking> findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(Long itemOwnerId, LocalDateTime start);

    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStart(Long itemOwnerId, BookingStatus status);

    Booking findByItem_IdAndStartAfterOrderByStartDesc(Long itemId, LocalDateTime start);

    Booking findByItem_IdAndEndBeforeOrderByStartDesc(Long itemId, LocalDateTime end);

}
