package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.exceptions.InvalidStatusException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.userDto.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final UserRepository userRepository;

    @Override
    public BookingDto create(Long userId, BookingDto bookingDto) {
        Booking booking = bookingMapper.dtoToBooking(bookingDto);
        User booker = userMapper.dtoToUser(userService.getUser(userId));
        Item item = itemMapper.dtoToItem(itemService.getItem(bookingDto.getItemId()));

        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);
        return bookingMapper.bookingToDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto updateAvailable(Long userId, Long itemId, BookingDto bookingDto) {
        return null;
    }

    @Override
    public BookingDto getBooking(Long userId, Long bookingId) {
        Booking booking = getBooking(bookingId);
        long bookerId = booking.getBooker().getId();
        long ownerId = booking.getItem().getOwner().getId();
        if(bookerId != userId && ownerId != userId){
            throw new NotFoundException("U not booker or owner, sorry");
        }
        return bookingMapper.bookingToDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingByUser(Long userId, String state) {

        switch (state){
            case "ALL":
                return bookingRepository.findAllByBooker_IdOrderByStartDesc(userId).stream().
                        map(bookingMapper::bookingToDto).collect(Collectors.toList());
            case "CURRENT":
                return bookingRepository.findAllByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(userId,
                        LocalDateTime.now(), LocalDateTime.now()).
                        stream().map(bookingMapper::bookingToDto).collect(Collectors.toList());
            case "PAST":
                return bookingRepository.findAllByBooker_IdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now()).
                        stream().map(bookingMapper::bookingToDto).collect(Collectors.toList());
            case "FUTURE":
                return bookingRepository.findAllByBooker_IdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now()).
                        stream().map(bookingMapper::bookingToDto).collect(Collectors.toList());
            case "WAITING":
                return bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, BookingStatus.WAITING).
                        stream().map(bookingMapper::bookingToDto).collect(Collectors.toList());
            case "REJECTED":
                return bookingRepository.findAllByBooker_IdAndStatusOrderByStart(userId, BookingStatus.REJECTED).
                        stream().map(bookingMapper::bookingToDto).collect(Collectors.toList());
            default:
                throw new InvalidStatusException();
            }
    }

    @Override
    public List<BookingDto> getAllBookingByOwner(Long ownerId, String owner) {
        return null;
    }

    @Override
    public BookingDto updateBookingApprove(Long ownerId, long bookingId, boolean approve) {
        Booking booking = getBooking(bookingId);
       if(!booking.getItem().getOwner().getId().equals(ownerId)){
           throw new NotFoundException("Item owner not found");
       }
       if(approve){
           booking.setStatus(BookingStatus.APPROVED);
       } else{
           booking.setStatus(BookingStatus.REJECTED);
       }
        return bookingMapper.bookingToDto(bookingRepository.save(booking));
    }

    private Booking getBooking(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException("booking not found"));
    }
}
