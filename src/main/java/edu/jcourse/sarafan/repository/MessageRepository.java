package edu.jcourse.sarafan.repository;

import edu.jcourse.sarafan.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}