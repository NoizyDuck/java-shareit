package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto bookingToDto (Booking booking);
    Booking dtoToBooking(BookingDto bookingDto);
    @Mapping (target = "itemName", source = "item.name")
    @Mapping (target = "bookerId", source = "booker.id")
    @Mapping(target = "itemId", source = "item.id")
    BookingDtoLong longDtoToBooking(Booking booking);
}
