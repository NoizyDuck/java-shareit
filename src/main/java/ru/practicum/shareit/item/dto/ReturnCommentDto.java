package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCommentDto {
    private long id;
    private String text;
    private String authorName;
    private LocalDateTime createdTime;
//    private Long id;
//    private String text;
//    private Item item;
//    private User author;
//    private LocalDateTime createdTime;

}

