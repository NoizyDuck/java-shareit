package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.exceptions.IncorrectParameterException;
import ru.practicum.shareit.exceptions.InvalidStatusException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @Override
    public ReturnBookingDto create(Long userId, BookingDto bookingDto) {
        Booking booking = bookingMapper.dtoToBooking(bookingDto);
        User booker = userMapper.dtoToUser(userService.getUser(userId));
        Item item = itemMapper.dtoToItem(itemService.getItem(bookingDto.getItemId(), userId));
        User owner = item.getOwner();
        if (booker.getId().equals(owner.getId())) {
            throw new NotFoundException("owner cant be a booker");
        }
        if (!item.isAvailable()) {
            throw new IncorrectParameterException("Item is unavailable");
        }
        if (booking.getStart().isBefore(LocalDateTime.now()) ||
                booking.getEnd().isBefore(LocalDateTime.now()) ||
                booking.getEnd().isBefore(booking.getStart())) {
            throw new IncorrectParameterException("Booking start time or booking end time is incorrect");
        }
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);
        return bookingMapper.returnDtoToBooking(bookingRepository.save(booking));
    }


    @Override
    public ReturnBookingDto getBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("booking"));
        Item item = booking.getItem();
        long bookerId = booking.getBooker().getId();
        long ownerId = item.getOwner().getId();
        if (bookerId != userId && ownerId != userId) {
            throw new NotFoundException("U not booker or owner, sorry");
        }
        return bookingMapper.returnDtoToBooking(booking);
    }

    @Override
    public List<ReturnBookingDto> getAllBookingByUser(Long userId, String state) {
        userService.getUser(userId);
        switch (state) {
            case "ALL":
                return getReturnDtoList(bookingRepository.findAllByBooker_IdOrderByStartDesc(userId));
            case "CURRENT":
                return getReturnDtoList(bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                        LocalDateTime.now(), LocalDateTime.now()));
            case "PAST":
                return getReturnDtoList(bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now()));
            case "FUTURE":
                return getReturnDtoList(bookingRepository.findAllByBooker_IdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now()));
            case "WAITING":
                return getReturnDtoList(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, BookingStatus.WAITING));
            case "REJECTED":
                return getReturnDtoList(bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, BookingStatus.REJECTED));
            default:
                throw new InvalidStatusException();
        }
    }

    @Override
    public List<ReturnBookingDto> getAllBookingByOwner(Long ownerId, String state) {
        userService.getUser(ownerId);
        switch (state) {
            case "ALL":
                return getReturnDtoList(bookingRepository.findAllByItem_Owner_IdOrderByStartDesc(ownerId));
            case "CURRENT":
                return getReturnDtoList(bookingRepository.findAllByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(
                        ownerId, LocalDateTime.now(), LocalDateTime.now()));
            case "PAST":
                return getReturnDtoList(bookingRepository.findAllByItem_Owner_IdAndEndBeforeOrderByStartDesc(ownerId, LocalDateTime.now()));
            case "FUTURE":
                return getReturnDtoList(bookingRepository.findAllByItem_Owner_IdAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now()));
            case "WAITING":
                return getReturnDtoList(bookingRepository.findAllByItem_Owner_IdAndStatusOrderByStart(ownerId, BookingStatus.WAITING));
            case "REJECTED":
                return getReturnDtoList(bookingRepository.findAllByItem_Owner_IdAndStatusOrderByStart(ownerId, BookingStatus.REJECTED));
            default:
                throw new InvalidStatusException();
        }
    }


    @Override
    public ReturnBookingDto updateBookingApprove(Long ownerId, long bookingId, boolean approve) {
        Booking booking = getBooking(bookingId);
        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new NotFoundException("Item owner not found");
        }
        if (approve && booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new IncorrectParameterException("Booking status already approved ");
        }
        if (approve) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingMapper.returnDtoToBooking(bookingRepository.save(booking));
    }


    private Booking getBooking(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("booking not found"));
    }

    private List<ReturnBookingDto> getReturnDtoList(List<Booking> bookingList) {
        return bookingList.stream().map(bookingMapper::returnDtoToBooking).collect(Collectors.toList());
    }
}
