package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;


@Data
@Entity
@Table(name = "items")
    public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_name")
    private String name;
    @Column
    private String description;
    @Column
    private boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column (name = "request_id")
    private Integer request;


}
