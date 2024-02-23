package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserSubscriptionMapper.class})
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "subscribers", ignore = true)
    void copy(UserDto source, @MappingTarget User target);

    @InheritInverseConfiguration
    User toEntity(UserDto userDto);
}