package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment dtoToComment(CommentDto commentDto);

    ReturnCommentDto returnDtoToComment(Comment comment);
}
