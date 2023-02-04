package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDto bookingToDto (Booking booking);
    Booking dtoToBooking(BookingDto bookingDto);
}
