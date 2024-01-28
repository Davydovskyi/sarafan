package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.MessageDto;
import edu.jcourse.sarafan.entity.Message;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper {

    MessageDto toDto(Message message);

    // хорошое решение для update или patch
//    @Mapping(target = "field1", source = "source.field1", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // можно либо прописать интересующие поля или задать для всего интерфейса
    void copy(MessageDto source, @MappingTarget Message target);

    @InheritInverseConfiguration
    Message toEntity(MessageDto messageDto);
}