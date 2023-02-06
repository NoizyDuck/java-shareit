package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto commentToDto(Comment comment);
    Comment dtoToComment(CommentDto commentDto);
}
