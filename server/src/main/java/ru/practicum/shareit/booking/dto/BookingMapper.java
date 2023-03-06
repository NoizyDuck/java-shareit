package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking dtoToBooking(BookingDto bookingDto);

    ReturnBookingDto returnDtoToBooking(Booking booking);
    @Mapping(target = "bookerId", source = "booker.id")
    ShortBookingDto bookingToShortBookingDto(Booking booking);
}
