package com.effortstone.backend.domain.routine.entity;

import java.util.Arrays;

public enum RoutineTheme {
    HEALTH(0),
    EMOTION(1),
    STUDY(2),
    FINANCE(3),
    RELATIONSHIP(4),
    MORNING(5),
    LEISURE(6);

    private final int number;

    RoutineTheme(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

