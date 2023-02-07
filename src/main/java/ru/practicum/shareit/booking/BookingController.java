package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

   @PostMapping
    public ReturnBookingDto createBooking (@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @RequestBody BookingDto bookingDto){
       log.debug("New booking created by user id #" + userId);
        return bookingService.create(userId, bookingDto);
   }
//@PostMapping
//public Booking createBooking (@RequestHeader("X-Sharer-User-Id") Long userId,
//                                       @RequestBody BookingDto bookingDto){
//    log.debug("New booking created by user id #" + userId);
//    return bookingService.create(userId, bookingDto);
//}


//   @PatchMapping("/{itemId}")
//    public BookingDto updateBooking (@RequestHeader("X-Sharer-User-Id") Long userId,
//                                     @PathVariable Long itemId,
//                                     @RequestBody BookingDto bookingDto){
//       log.debug("Booking available update");
//       return bookingService.updateAvailable(userId,itemId,bookingDto);
//   }

   @PatchMapping("/{bookingId}")
   public ReturnBookingDto updateBookingApprove (@RequestHeader("X-Sharer-User-Id") long ownerId,
                                           @PathVariable long bookingId,
                                           @RequestParam boolean approved){
       log.debug("Update booking approve");
       return bookingService.updateBookingApprove(ownerId,bookingId,approved);
   }

//   @GetMapping("/{bookingId}")
//    public BookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
//                                 @PathVariable Long bookingId){
//       log.debug("Get request booking for user id " + userId);
//       return bookingService.getBooking(userId, bookingId);
//   }
    @GetMapping("/{bookingId}")
    public ReturnBookingDto getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @PathVariable Long bookingId){
        log.debug("Get request booking for user id " + userId);
        return bookingService.getBooking(userId, bookingId);
    }

   @GetMapping
    public List<ReturnBookingDto> getAllBookingByUser(@RequestHeader ("X-Sharer-User-Id") Long userId,
                                                @RequestParam(defaultValue = "ALL") String state){
       log.debug("Get request for all bookings of user id" + userId);
       return bookingService.getAllBookingByUser(userId, state);
   }

   @GetMapping("/owner")
    public List<ReturnBookingDto> getAllBookingByOwner (@RequestHeader (name = "X-Sharer-User-Id") Long ownerId,
                                                  @RequestParam (defaultValue = "ALL") String state){
       log.debug("Get request by owner id " + ownerId);
       return bookingService.getAllBookingByOwner(ownerId, state);
   }




}
