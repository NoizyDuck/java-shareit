package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {
    @InjectMocks
    private BookingController bookingController;
    @Mock
    private BookingService bookingService;

    @Test
    void createBooking_whenBookingCreated_thenReturnStatusOkAndReturnBookingDtoBody() {
        long userId = 0L;
        BookingDto bookingDto = new BookingDto();
        ReturnBookingDto expectedDto = new ReturnBookingDto();
        when(bookingService.create(userId, bookingDto)).thenReturn(expectedDto);

        ResponseEntity<ReturnBookingDto> response = bookingController.createBooking(userId, bookingDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(bookingService).create(userId, bookingDto);
    }

    @Test
    void updateBookingApprove_whenBookingUpdate_thenReturnStatusOkAndReturnBookingDtoBody() {
        long ownerId = 0L;
        long bookingId = 0L;
        boolean approve = true;
        ReturnBookingDto expectedDto = new ReturnBookingDto();
        when(bookingService.updateBookingApprove(ownerId, bookingId, approve)).thenReturn(expectedDto);

        ResponseEntity<ReturnBookingDto> response = bookingController.updateBookingApprove(ownerId, bookingId, approve);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(bookingService).updateBookingApprove(ownerId, bookingId, approve);
    }

    @Test
    void getBooking_whenBookingGet_thenReturnStatusOkAndReturnBookingDtoBody() {
        long ownerId = 0L;
        long bookingId = 0L;
        ReturnBookingDto expectedDto = new ReturnBookingDto();
        when(bookingService.getBooking(ownerId, bookingId)).thenReturn(expectedDto);

        ResponseEntity<ReturnBookingDto> response = bookingController.getBooking(ownerId, bookingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(bookingService).getBooking(ownerId, bookingId);
    }

    @Test
    void getAllBookingByUserAndState_whenBookingGet_thenReturnStatusOkAndReturnBookingDtoListBody() {
        long userId = 0L;
        String state = "All";
        Integer from = 1;
        Integer size = 1;
        List<ReturnBookingDto> expectedDto = List.of(new ReturnBookingDto());
        when(bookingService.getAllBookingByUser(userId, state, from, size)).thenReturn(expectedDto);

        ResponseEntity<List<ReturnBookingDto>> response = bookingController.getAllBookingByUser(userId, state, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(bookingService).getAllBookingByUser(userId, state, from, size);
    }

    @Test
    void getAllBookingByOwnerAndState_whenBookingGet_thenReturnStatusOkAndReturnBookingDtoListBody() {
        long ownerId = 0L;
        String state = "All";
        Integer from = 1;
        Integer size = 1;
        List<ReturnBookingDto> expectedDto = List.of(new ReturnBookingDto());
        when(bookingService.getAllBookingByOwner(ownerId, state, from, size)).thenReturn(expectedDto);

        ResponseEntity<List<ReturnBookingDto>> response = bookingController.getAllBookingByOwner(ownerId, state, from, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(bookingService).getAllBookingByOwner(ownerId, state, from, size);

    }
}