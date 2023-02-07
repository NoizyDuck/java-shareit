package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBooker_IdAndEndIsBefore(Long bookerId, LocalDateTime end, Sort sort);
    List<Booking> findAllByBooker_IdOrderByStartDesc(Long booker_id);
    List<Booking> findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long booker_id, LocalDateTime start, LocalDateTime end);
    List<Booking> findAllByBooker_IdAndEndBeforeOrderByStartDesc(Long booker_id, LocalDateTime end);
    List<Booking> findAllByBooker_IdAndStartAfterOrderByStartDesc(Long booker_id, LocalDateTime start);
    List<Booking> findAllByBooker_IdAndStatusOrderByStart(Long booker_id, BookingStatus status);
    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(Long item_owner_id);
    List<Booking> findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long item_owner_id, LocalDateTime start, LocalDateTime end);
    List<Booking>findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(Long item_owner_id, LocalDateTime end);
    List<Booking>findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(Long item_owner_id, LocalDateTime start);
    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStart(Long item_owner_id, BookingStatus status);
    Booking findByItem_IdAndStartAfterOrderByStartDesc(Long item_id, LocalDateTime start);
    Booking findByItem_IdAndEndBeforeOrderByStartDesc(Long item_id, LocalDateTime end);


    List<Booking> findAllByItem_IdOrderByStartDesc(Long id);
}
