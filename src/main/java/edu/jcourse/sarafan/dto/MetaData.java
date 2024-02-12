package edu.jcourse.sarafan.dto;

import lombok.Builder;

@Builder
public record MetaData(
        String title,
        String description,
        String cover) {
}