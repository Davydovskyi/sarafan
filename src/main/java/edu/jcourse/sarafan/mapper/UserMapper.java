package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void copy(UserDto source, @MappingTarget User target);

    User toEntity(UserDto userDto);
}