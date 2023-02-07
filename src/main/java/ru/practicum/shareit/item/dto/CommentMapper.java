package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.item.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto commentToDto(Comment comment);
    Comment dtoToComment(CommentDto commentDto);
    @Mapping(target = "authorName", source = "author.name")
    ReturnCommentDto returnDtoToComment (Comment comment);
}
