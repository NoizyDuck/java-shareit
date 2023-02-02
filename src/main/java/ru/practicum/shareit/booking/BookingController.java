package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
   private final BookingService bookingService;

   @PostMapping
    public BookingDto createBooking (@RequestHeader("X-Sharer-User-Id") Long userId,
                                  @RequestBody BookingDto bookingDto){
       log.debug("New booking created by user id #" + userId);
        return bookingService.create(userId, bookingDto);
   }

   @PatchMapping("/{itemId}")
    public BookingDto updateBooking (@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable Long itemId,
                                     @RequestBody BookingDto bookingDto){
       log.debug("Booking available update");
       return bookingService.updateAvailable(userId,itemId,bookingDto);
   }



}
