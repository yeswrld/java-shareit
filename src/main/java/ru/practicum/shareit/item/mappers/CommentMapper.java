package ru.practicum.shareit.item.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper MAPPER = Mappers.getMapper(CommentMapper.class);

    //    @Mapping(source = "id", target = "id")
//    @Mapping(source = "text", target = "text")
    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "itemId", source = "item.id")
//    @Mapping(source = "created", target = "created")
    CommentDto toCommentDto(Comment comment);

    Comment toComment(CommentDto commentDto);

    default List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }
}
