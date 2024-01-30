package edu.jcourse.sarafan.entity;

import java.util.Arrays;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender find(String name) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(Gender.OTHER);
    }
}