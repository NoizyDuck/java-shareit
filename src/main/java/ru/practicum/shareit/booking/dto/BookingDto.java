package ru.practicum.shareit.booking.dto;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    @NotNull
  private   Long itemId;
    @NotNull
   private LocalDateTime start;
    @NotNull
   private LocalDateTime end;
}
