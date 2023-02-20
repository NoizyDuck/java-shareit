package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.exceptions.IncorrectParameterException;
import ru.practicum.shareit.exceptions.InvalidStatusException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.userDto.UserDto;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ItemServiceImpl itemServiceImpl;
    @Mock
    private ItemService itemService;
    @Mock
    UserService userService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Spy
    private BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Spy
    private ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    @InjectMocks
    private BookingServiceImpl bookingService;
    @Captor
    private ArgumentCaptor<Booking> bookingArgumentCaptor;

    User user = new User(1L, "name", "email");

    Item item = new Item(1L, "itemName", "itemDescription", true, user, 1L);


    @Test
    void create() {
        long userId = 1;
        long itemId = 1;
        long bookingId = 1;
        User user2 = new User(2L, "name2", "email2");
        item.setOwner(user2);
        BookingDto bookingDto = new BookingDto(itemId, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        ItemDto itemDto = itemMapper.itemToDto(item);
        UserDto userDto = userMapper.userToDto(user);
        Booking booking = new Booking (bookingId, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.WAITING);

        when(userService.getUser(userId)).thenReturn(userDto);
        when(itemService.getItem(itemId, userId)).thenReturn(itemDto);
//        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        ReturnBookingDto expectedDto = bookingMapper.returnDtoToBooking(booking);

        ReturnBookingDto actualDto = bookingService.create(userId, bookingDto);

        assertEquals(expectedDto, actualDto);
        verify(bookingRepository).save(booking);
    }

    @Test
    void getBooking_whenInvoked_thenReturnReturnBookingDto() {
        long userId = 1;
        long bookingId = 1;
        Booking booking = new Booking(bookingId, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.WAITING);
        ReturnBookingDto expectedDto = bookingMapper.returnDtoToBooking(booking);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        ReturnBookingDto actualDto = bookingService.getBooking(userId, bookingId);

        assertEquals(expectedDto, actualDto);
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    void getBooking_whenUserIsNotBookerOrOwner_thenThrowNotFoundException() {
        long bookingId = 1;
        Booking booking = new Booking(bookingId, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.WAITING);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        assertThrows(NotFoundException.class, () -> bookingService.getBooking(0L, bookingId));
    }

    @Test
    void getBooking_whenBookingNotFound_thenThrowNotFoundException() {
        long bookingId = 1;
        long userId = 1;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.getBooking(userId, bookingId));
    }

    @Test
    void getAllByBooker_whenInvokedWithStatusALL_thenCallFindAllByBooker_IdOrderByStartDesc() {
        bookingService.getAllBookingByUser(1L, "ALL", null, null);
        verify(bookingRepository).findAllByBooker_IdOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getAllByBooker_whenInvokedWithStatusCURRENT_thenCallFindAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc() {
        bookingService.getAllBookingByUser(1L, "CURRENT", null, null);
        verify(bookingRepository)
                .findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByBooker_whenInvokedWithStatusPAST_thenCallFindAllByBooker_IdAndEndBeforeOrderByStartDesc() {
        bookingService.getAllBookingByUser(1L, "PAST", null, null);
        verify(bookingRepository).findAllByBooker_IdAndEndBeforeOrderByStartDesc(anyLong(), any());
    }

        @Test
        void getAllByBooker_whenInvokedWithStatusFUTURE_thenCallFindAllByBooker_IdAndStartAfterOrderByStartDesc() {
            bookingService.getAllBookingByUser(1L, "PAST", null, null);
            verify(bookingRepository).findAllByBooker_IdAndStartAfterOrderByStartDesc(anyLong(), any());
        }

    @Test
    void getAllByBooker_whenInvokedWithStatusWAITING_thenCallFindAllByBooker_IdAndStatusOrderByStart() {
        bookingService.getAllBookingByUser(1L, "WAITING", null, null);
        verify(bookingRepository).findAllByBooker_IdAndStatusOrderByStart(anyLong(), BookingStatus.WAITING);
    }

    @Test
    void getAllByBooker_whenInvokedWithStatusREJECTED_thenCallFindAllByBooker_IdAndStatusOrderByStart() {
        bookingService.getAllBookingByUser(1L, "REJECTED", null, null);
        verify(bookingRepository).findAllByBooker_IdAndStatusOrderByStart(anyLong(), BookingStatus.REJECTED);
    }

    @Test
    void getAllByBooker_whenStatusInvalid_thenThrowInvalidStatusException() {
        assertThatThrownBy(() -> {
            bookingService.getAllBookingByUser(1L, "InvalidStatus", null, null);
        }).isInstanceOf(InvalidStatusException.class);
    }

    @Test
    void getAllByOwner_whenInvokedWithStatusALL_thenCallFindAllByItem_Owner_IdOrderByStartDesc() {
        bookingService.getAllBookingByOwner(1L, "ALL", null, null);
        verify(bookingRepository).findAllByItem_Owner_IdOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getAllByOwner_whenInvokedWithStatusCURRENT_thenCallFindAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc() {
        bookingService.getAllBookingByOwner(1L, "CURRENT", null, null);
        verify(bookingRepository).findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(anyLong(), any(), any());
    }

    @Test
    void getAllByOwner_whenInvokedWithStatusPAST_thenCallFindAllByItem_Owner_IdAndEndBeforeOrderByStartDesc() {
        bookingService.getAllBookingByOwner(1L, "PAST", null, null);
        verify(bookingRepository).findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getAllByOwner_whenInvokedWithStatusFUTURE_thenCallFindAllByItem_Owner_IdAndStartAfterOrderByStartDesc() {
        bookingService.getAllBookingByOwner(1L, "FUTURE", null, null);
        verify(bookingRepository).findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(anyLong(), any());
    }

    @Test
    void getAllByOwner_whenInvokedWithStatusWAITING_thenCallFindAllByItem_Owner_IdAndStatusOrderByStart() {
        bookingService.getAllBookingByOwner(1L, "WAITING", null, null);
        verify(bookingRepository).findAllByItem_Owner_IdAndStatusOrderByStart(anyLong(), BookingStatus.WAITING);
    }

    @Test
    void getAllByOwner_whenInvokedWithStatusREJECTED_thenCallFindAllByItem_Owner_IdAndStatusOrderByStart() {
        bookingService.getAllBookingByOwner(1L, "REJECTED", null, null);
        verify(bookingRepository).findAllByItem_Owner_IdAndStatusOrderByStart(anyLong(), BookingStatus.REJECTED);
    }

    @Test
    void getAllByOwner_whenStatusInvalid_thenThrowInvalidStatusException() {
        assertThatThrownBy(() -> {
            bookingService.getAllBookingByUser(1L, "InvalidStatus", null, null);
        }).isInstanceOf(InvalidStatusException.class);
    }

    @Test
    void updateBookingApprove_whenInvokedStatusApproveTrue_thenReturnStatusAPPROVED() {
        long ownerId = 1;
        long bookingId = 1;
        Booking booking = new Booking(bookingId, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.WAITING);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

       bookingService.updateBookingApprove(ownerId, bookingId, true);

        verify(bookingRepository).save(bookingArgumentCaptor.capture());
        Booking savedBooking = bookingArgumentCaptor.getValue();

        assertEquals(BookingStatus.APPROVED, savedBooking.getStatus());
    }
    @Test
    void updateBookingApprove_whenInvokedStatusApproveFalse_thenReturnStatusREJECTED() {
        long ownerId = 1;
        long bookingId = 1;
        Booking booking = new Booking(bookingId, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.WAITING);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.updateBookingApprove(ownerId, bookingId, false);

        verify(bookingRepository).save(bookingArgumentCaptor.capture());
        Booking savedBooking = bookingArgumentCaptor.getValue();

        assertEquals(BookingStatus.REJECTED, savedBooking.getStatus());
    }
    @Test
    void updateBookingApprove_whenStatusAlreadyAPPROVED_thenThrowIncorrectParameterException() {
        long ownerId = 1;
        long bookingId = 1;
        Booking booking = new Booking(bookingId, LocalDateTime.now(), LocalDateTime.now(), item, user, BookingStatus.APPROVED);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        assertThrows(IncorrectParameterException.class, ()-> bookingService.updateBookingApprove(ownerId, bookingId, true));
    }
}