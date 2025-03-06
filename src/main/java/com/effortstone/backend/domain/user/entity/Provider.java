package com.effortstone.backend.domain.user.entity;

public enum Provider {
    ANONYMOUS(0),
    GOOGLE(1),
    APPLE(2),
    NUMBER(3);

    private final int code;

    Provider(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Provider fromCode(int code) {
        for (Provider provider : values()) {
            if (provider.getCode() == code) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider code: " + code);
    }
}

