package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking dtoToBooking(BookingDto bookingDto);

    ReturnBookingDto returnDtoToBooking(Booking booking);

    ShortBookingDto bookingToShortBookingDto(Booking booking);
}
