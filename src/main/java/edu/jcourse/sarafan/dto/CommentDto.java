package edu.jcourse.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.jcourse.sarafan.entity.View;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentDto(
        @JsonView(View.IdName.class)
        Long id,
        @JsonView(View.IdName.class)
        String text,
        // second way to solve circular references
//        @JsonBackReference + @JsonManagedReference in MessageDto on CommentDto field
        @JsonView(View.FullComment.class)
        MessageDto message,
        @JsonView(View.IdName.class)
        UserDto user) {
}