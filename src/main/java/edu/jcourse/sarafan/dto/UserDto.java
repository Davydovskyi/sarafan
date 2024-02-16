package edu.jcourse.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.jcourse.sarafan.entity.Gender;
import edu.jcourse.sarafan.entity.Role;
import edu.jcourse.sarafan.entity.View;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@FieldNameConstants
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
public class UserDto implements Serializable {
    @JsonView(View.IdName.class)
    String id;
    @JsonView(View.IdName.class)
    String name;
    @JsonView(View.IdName.class)
    String userPic;
    String email;
    Gender gender;
    Role role;
    String locale;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastVisit;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;
}