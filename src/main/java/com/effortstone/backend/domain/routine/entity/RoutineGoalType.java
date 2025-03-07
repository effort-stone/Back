package com.effortstone.backend.domain.routine.entity;

import lombok.Getter;

@Getter
public enum RoutineGoalType {
    CHECK(0),
    TIME(1);

    private final int number;

    RoutineGoalType(int number) {
        this.number = number;
    }

    public static RoutineGoalType fromNumber(int number) {
        for (RoutineGoalType goalType : RoutineGoalType.values()) {
            if (goalType.getNumber() == number) {
                return goalType;
            }
        }
        throw new IllegalArgumentException("Unknown routine theme number: " + number);
    }
}
