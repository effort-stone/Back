package com.effortstone.backend.domain.user.entity;

public enum Provider {
    ANONYMOUS(0),
    GOOGLE(1),
    APPLE(2),
    NUMBER(3);

    private final int number;

    Provider(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

