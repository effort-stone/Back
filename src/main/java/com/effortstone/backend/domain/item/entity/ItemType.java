package com.effortstone.backend.domain.item.entity;

import lombok.Getter;

@Getter
public enum ItemType {
    PLAYER(0),
    SIDE(1),
    TOP(2),
    BACKGROUND(3);

    private final int number;

    ItemType(int number) {
        this.number = number;
    }

    public static ItemType fromNumber(int number) {
        for (ItemType type : ItemType.values()) {
            if (type.getNumber() == number) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ItemType number: " + number);
    }
}
