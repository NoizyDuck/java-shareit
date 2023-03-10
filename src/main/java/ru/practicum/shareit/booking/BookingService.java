package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;

import java.util.List;

public interface BookingService {
    ReturnBookingDto create(Long userId, BookingDto bookingDto);


    ReturnBookingDto getBooking(Long userId, Long bookingId);

    List<ReturnBookingDto> getAllBookingByOwner(Long ownerId, String owner, Integer from, Integer size);

    ReturnBookingDto updateBookingApprove(Long userId, long bookingId, boolean approve);

    List<ReturnBookingDto> getAllBookingByUser(Long userId, String state, Integer from, Integer size);
}
