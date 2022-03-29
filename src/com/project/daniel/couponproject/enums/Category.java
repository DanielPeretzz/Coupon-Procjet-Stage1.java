package com.project.daniel.couponproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Category {
    FOOD(1),
    ELECTRICITY(2),
    RESTAURANT(3),
    VACATION(4);

    @Getter
    private final int value;
}
