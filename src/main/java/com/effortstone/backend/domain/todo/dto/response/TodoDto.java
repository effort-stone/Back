package com.effortstone.backend.domain.todo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TodoDto {
    private Long todoCode; // todoCode
    private String todoName; // 투두 시간
    private LocalTime todoAlert; // 알람
    private LocalDate todoDate;    // 할일이 예정된 날짜
    private String todoDetail;
    private Boolean todoCompleted;
}
