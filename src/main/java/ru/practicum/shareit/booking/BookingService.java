package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto create(Long userId, BookingDto bookingDto);

    BookingDto updateAvailable(Long userId, Long itemId, BookingDto bookingDto);

    BookingDto getBooking(Long userId, Long bookingId);

    List<BookingDto> getAllBookingByUser(Long userId, String state);

    List<BookingDto> getAllBookingByOwner(Long ownerId, String owner);

    BookingDto updateBookingApprove(Long userId, long bookingId, boolean approve);
}
