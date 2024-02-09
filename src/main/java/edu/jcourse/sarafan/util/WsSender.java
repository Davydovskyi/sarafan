package edu.jcourse.sarafan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.jcourse.sarafan.dto.EventType;
import edu.jcourse.sarafan.dto.ObjectType;
import edu.jcourse.sarafan.dto.WsEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
public class WsSender {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public <T> BiConsumer<EventType, T> getSender(ObjectType type, Class<?> view) {
        ObjectWriter writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(view);

        return (EventType event, T payload) -> template.
                convertAndSend("/topic/activity",
                        new WsEventDto(type, event, toJson(payload, writer)));
    }

    private String toJson(Object payload, ObjectWriter writer) {
        try {
            return writer.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}