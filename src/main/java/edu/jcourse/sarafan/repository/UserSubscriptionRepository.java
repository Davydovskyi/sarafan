package edu.jcourse.sarafan.repository;

import edu.jcourse.sarafan.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findBySubscriberIdAndSubscriptionId(String subscriberId, String subscriptionId);
}