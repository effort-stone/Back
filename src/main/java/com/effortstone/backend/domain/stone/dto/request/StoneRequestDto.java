package com.effortstone.backend.domain.stone.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class StoneRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class StoneCreateRequest {
        private String stoneName;
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class StoneUpdateRequest {
        private Long stoneCode;
        private String stoneName;
        private Integer stoneLevel;
        private Long stoneExp;
    }
}
