package edu.jcourse.sarafan.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import edu.jcourse.sarafan.entity.View;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonView(View.FullMessage.class)
public record PageResponse<T>(List<T> content,
                              MetaData metaData) {

    public static <T> PageResponse<T> of(Page<T> page) {
        MetaData metaData = new MetaData(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
        return new PageResponse<>(page.getContent(), metaData);
    }

    @JsonView(View.FullMessage.class)
       @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record MetaData(int page,
                           int size,
                           long totalElements,
                           int totalPages) {
    }
}