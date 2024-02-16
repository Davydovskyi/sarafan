package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.CommentDto;
import edu.jcourse.sarafan.entity.Comment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MessageMapper.class, UserMapper.class})
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    @Named("WithoutMessage")
    @Mapping(target = "message", ignore = true)
    CommentDto toDtoWithoutMessage(Comment comment);

    @IterableMapping(qualifiedByName = "WithoutMessage")
    List<CommentDto> commentListToCommentDtoList(List<Comment> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "message", ignore = true)
    void copy(CommentDto source, @MappingTarget Comment target);

    @InheritInverseConfiguration(name = "toDto")
    Comment toEntity(CommentDto commentDto);
}