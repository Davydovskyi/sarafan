package edu.jcourse.sarafan.repository;

import edu.jcourse.sarafan.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = {"subscriptions", "subscribers"})
    Optional<User> findById(String id);
}