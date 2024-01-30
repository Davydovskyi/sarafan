package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDto toDto(User user);

    void copy(UserDto source, @MappingTarget User target);

    User toEntity(UserDto userDto);
}