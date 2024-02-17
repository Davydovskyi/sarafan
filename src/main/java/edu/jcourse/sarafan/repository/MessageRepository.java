package edu.jcourse.sarafan.repository;

import edu.jcourse.sarafan.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"comments", "comments.user"})
    Page<Message> findAll(Pageable pageable);
}