package edu.jcourse.sarafan.repository;

import edu.jcourse.sarafan.entity.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"comments", "comments.user"})
    List<Message> findAll();
}