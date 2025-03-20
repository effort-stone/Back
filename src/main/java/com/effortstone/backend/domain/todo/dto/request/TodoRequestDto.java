package com.effortstone.backend.domain.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TodoRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class TodoCreateRequest {
        private String title; // todoName -> title
        private String memo; // todoDetail -> memo
        private LocalDate dateTime; // todoDate -> dateTime (LocalDate -> String 변경)
        @Schema(example = "08:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime alram; // todoAlert -> alram

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    @Setter
    public static class TodoUpdateRequest {
        private Long id; // PRIMARY KEY AUTOINCREMENT (새로 추가됨)
        private String title; // todoName -> title
        private String memo; // todoDetail -> memo
        private LocalDate dateTime; // todoDate -> dateTime (LocalDate -> String 변경)
//        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDate completedDate; // 완료된 날짜 (새로 추가됨)
        @Schema(example = "08:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime alram; // todoAlert -> alram
    }
}
