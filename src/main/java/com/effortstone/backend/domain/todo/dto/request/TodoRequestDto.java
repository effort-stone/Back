package com.effortstone.backend.domain.todo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TodoRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class TodoCreateRequest {
        private String todoName;
        private String todoDetail;
        //Spring에서 LocalTime을 "HH:mm:ss" 형식의 문자열로 처리하도록 설정.
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime todoAlert;
        private LocalDate todoDate;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class TodoUpdateRequest {
        private String todoName;
        private String todoDetail;
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) // 시간만 변환
        private LocalTime todoAlert;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // 날짜 변환
        private LocalDate todoDate;
        private Boolean todoCompleted;
    }
}
