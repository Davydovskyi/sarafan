package edu.jcourse.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.jcourse.sarafan.entity.View;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserSubscriptionDto(
        @JsonView(View.IdName.class)
        Long id,
        @JsonView(View.IdName.class)
        UserDto subscriber,
        @JsonView(View.IdName.class)
        UserDto subscription,
        @JsonView(View.IdName.class)
        boolean isSubscribed) {
}