package edu.jcourse.sarafan.service;

import edu.jcourse.sarafan.dto.UserSubscriptionDto;
import edu.jcourse.sarafan.entity.UserSubscription;
import edu.jcourse.sarafan.mapper.UserSubscriptionMapper;
import edu.jcourse.sarafan.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSubscriptionService {
    private final UserSubscriptionMapper userSubscriptionMapper;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Transactional
    public UserSubscriptionDto subscribe(UserSubscriptionDto userSubscription) {
        String subscriberId = userSubscription.subscriber().getId();
        String subscriptionId = userSubscription.subscription().getId();

        if (Objects.equals(subscriberId, subscriptionId)) {
            return userSubscription;
        }

        Optional<UserSubscription> subscription = userSubscriptionRepository.findBySubscriberIdAndSubscriptionId(subscriberId, subscriptionId);
        if (subscription.isPresent()) {
            userSubscriptionRepository.delete(subscription.get());
            return userSubscription;
        }

        return Optional.of(userSubscription)
                .map(userSubscriptionMapper::toEntity)
                .map(userSubscriptionRepository::save)
                .map(userSubscriptionMapper::toDto)
                .orElseThrow();
    }
}