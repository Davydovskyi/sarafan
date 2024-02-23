package edu.jcourse.sarafan.mapper;

import edu.jcourse.sarafan.dto.UserDto;
import edu.jcourse.sarafan.dto.UserSubscriptionDto;
import edu.jcourse.sarafan.entity.User;
import edu.jcourse.sarafan.entity.UserSubscription;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class})
public interface UserSubscriptionMapper {

    @Mapping(target = "isSubscribed", constant = "true")
    UserSubscriptionDto toDto(UserSubscription userSubscription);

    @InheritInverseConfiguration
    UserSubscription toEntity(UserSubscriptionDto userSubscriptionDto);

    @IterableMapping(qualifiedByName = "toDtoIdOnly")
    List<UserSubscriptionDto> toDtoList(List<UserSubscription> userSubscriptions);

    @Named("toDtoIdOnly")
    default UserSubscriptionDto toDtoIdOnly(UserSubscription userSubscription) {
        if (userSubscription == null) {
            return null;
        }

        UserSubscriptionDto.UserSubscriptionDtoBuilder userSubscriptionDto = UserSubscriptionDto.builder();
        userSubscriptionDto.id(userSubscription.getId());

        User subscription = userSubscription.getSubscription();
        if (subscription != null) {
            userSubscriptionDto.subscription(UserDto.builder().id(subscription.getId()).build());
        }

        User subscriber = userSubscription.getSubscriber();
        if (subscriber != null) {
            userSubscriptionDto.subscriber(UserDto.builder().id(subscriber.getId()).build());
        }

        return userSubscriptionDto.build();
    }
}