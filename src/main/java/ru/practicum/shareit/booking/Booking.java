package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Table(name = "booking")

public class Booking {
    @Id
    private int id;
    private LocalDate start;
    private LocalDate end;
    private Item item;
    private User booker;
    private String status;

}
