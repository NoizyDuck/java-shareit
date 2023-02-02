package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

public interface BookingService {
    BookingDto create(Long userId, BookingDto bookingDto);

    BookingDto updateAvailable(Long userId, Long itemId, BookingDto bookingDto);
}
