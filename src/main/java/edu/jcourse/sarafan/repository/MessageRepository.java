package edu.jcourse.sarafan.repository;

import edu.jcourse.sarafan.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepository {

    private final List<Message> messages = new ArrayList<>();
    private Long id = 4L;

    public MessageRepository() {
        messages.add(new Message(1L, "Hello"));
        messages.add(new Message(2L, "Hi"));
        messages.add(new Message(3L, "Yo"));
    }

    public Optional<Message> findById(Long id) {
        return messages.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public List<Message> findAll() {
        return messages;
    }

    public Message save(Message message) {
        message.setId(id++);
        messages.add(message);
        return message;
    }

    public Optional<Message> update(Long id, Message message) {
        return findById(id)
                .map(m -> {
                    m.setText(message.getText());
                    return m;
                });
    }

    public boolean deleteById(Long id) {
        return messages.removeIf(m -> m.getId().equals(id));
    }
}