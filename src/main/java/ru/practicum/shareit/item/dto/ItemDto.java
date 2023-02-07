package ru.practicum.shareit.item.dto;

import lombok.*;
import org.springframework.data.annotation.Transient;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.ShortBookingDto;
import ru.practicum.shareit.item.Comment;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private User owner;
    private Integer request;
//    private Long commentsLength;
    private  ShortBookingDto lastBooking;
    private ShortBookingDto nextBooking;
    private List<ReturnCommentDto> comments;
}
