package com.effortstone.backend.domain.item.entity;

import lombok.Getter;

@Getter
public enum PriceType {
    FREE(0), // 무료
    PAID(1); // 유료

    private final int number;

    PriceType(int number) {
        this.number = number;
    }

    public static PriceType fromNumber(int number) {
        for (PriceType type : PriceType.values()) {
            if (type.getNumber() == number) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PriceType number: " + number);
    }
}
