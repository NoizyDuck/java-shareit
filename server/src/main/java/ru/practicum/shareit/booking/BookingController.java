package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ReturnBookingDto> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                          @RequestBody BookingDto bookingDto) {
        log.debug("New booking created by user id #" + userId);
        return ResponseEntity.ok(bookingService.create(userId, bookingDto));
    }

    @PatchMapping("/{bookingId}")
    public  ResponseEntity<ReturnBookingDto> updateBookingApprove(@RequestHeader("X-Sharer-User-Id") long ownerId,
                                                 @PathVariable long bookingId,
                                                 @RequestParam boolean approved) {
        log.debug("Update booking approve");
        return ResponseEntity.ok(bookingService.updateBookingApprove(ownerId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public  ResponseEntity<ReturnBookingDto> getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @PathVariable Long bookingId) {
        log.debug("Get request booking for user id " + userId);
        return ResponseEntity.ok(bookingService.getBooking(userId, bookingId));
    }


    @GetMapping
    public  ResponseEntity<List<ReturnBookingDto>> getAllBookingByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                      @RequestParam(defaultValue = "ALL") String state,
                                                      @RequestParam(required = false) Integer from,
                                                      @RequestParam(required = false) Integer size) {
        log.debug("Get request for all bookings of user id" + userId);
        return ResponseEntity.ok(bookingService.getAllBookingByUser(userId, state, from, size));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<ReturnBookingDto>> getAllBookingByOwner(@RequestHeader(name = "X-Sharer-User-Id") Long ownerId,
                                                       @RequestParam(defaultValue = "ALL") String state,
                                                       @RequestParam(required = false) Integer from,
                                                       @RequestParam(required = false) Integer size) {
        log.debug("Get request by owner id " + ownerId);
        return ResponseEntity.ok(bookingService.getAllBookingByOwner(ownerId, state, from, size));
    }


}
