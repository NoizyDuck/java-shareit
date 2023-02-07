package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;

import java.util.List;

public interface BookingService {
    ReturnBookingDto create(Long userId, BookingDto bookingDto);

    BookingDto updateAvailable(Long userId, Long itemId, BookingDto bookingDto);

    ReturnBookingDto getBooking(Long userId, Long bookingId);

    List<ReturnBookingDto> getAllBookingByUser(Long userId, String state);

    List<ReturnBookingDto> getAllBookingByOwner(Long ownerId, String owner);

    ReturnBookingDto updateBookingApprove(Long userId, long bookingId, boolean approve);
}
