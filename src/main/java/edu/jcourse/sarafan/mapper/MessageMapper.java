package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.entity.Message;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
//        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper {

    MessageDto toDto(Message message);

    // хорошое решение для update или patch
//    @Mapping(target = "field1", source = "source.field1", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // можно либо прописать интересующие поля или задать для всего интерфейса
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "text", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(MessageDto source, @MappingTarget Message target);

    @InheritInverseConfiguration
    Message toEntity(MessageDto messageDto);
}