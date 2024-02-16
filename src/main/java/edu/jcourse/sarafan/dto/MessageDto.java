package edu.jcourse.sarafan.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.jcourse.sarafan.entity.View;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

@FieldNameConstants
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
// first way
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // how to solve circular references
public record MessageDto(
        @JsonView(View.Id.class)
        Long id,

        @JsonView(View.IdName.class)
        String text,
        @JsonView(View.FullMessage.class)
        UserDto user,

        @JsonView(View.FullMessage.class)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonView(View.FullMessage.class)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,

        @JsonView(View.FullMessage.class)
        String link,
        @JsonView(View.FullMessage.class)
        @JsonProperty("link_title")
        String linkTitle,
        @JsonView(View.FullMessage.class)
        @JsonProperty("link_description")
        String linkDescription,
        @JsonView(View.FullMessage.class)
        @JsonProperty("link_cover")
        String linkCover,

        // second way to solve circular references
//        @JsonManagedReference + @JsonBackReference in CommentDto on MessageDto field
        @JsonView(View.FullMessage.class)
        List<CommentDto> comments) {
}