package edu.jcourse.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.jcourse.sarafan.entity.View;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonView(View.Id.class)
public record WsEventDto(
        ObjectType type,
        EventType event,
        @JsonRawValue
        String body) {
}