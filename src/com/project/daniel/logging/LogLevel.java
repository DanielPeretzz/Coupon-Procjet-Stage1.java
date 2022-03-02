package com.project.daniel.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum LogLevel {
    ERROR(1),
    WARNING(2),
    INFO(3),
    DEBUG(4);

    @Getter
    private final int value;
}
