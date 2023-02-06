package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoLong;

import java.util.List;

public interface BookingService {
    BookingDtoLong create(Long userId, BookingDto bookingDto);

    BookingDto updateAvailable(Long userId, Long itemId, BookingDto bookingDto);

    BookingDtoLong getBooking(Long userId, Long bookingId);

    List<BookingDto> getAllBookingByUser(Long userId, String state);

    List<BookingDto> getAllBookingByOwner(Long ownerId, String owner);

    BookingDto updateBookingApprove(Long userId, long bookingId, boolean approve);
}
