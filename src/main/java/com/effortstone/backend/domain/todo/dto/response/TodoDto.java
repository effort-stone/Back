package com.effortstone.backend.domain.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TodoDto {
    private Long todoCode; // todoCode
    private String todoName; // 투두 이름
    @Schema(example = "08:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime todoAlert; // 알람 여부
    private LocalDate todoDate;    // 할일이 예정된 날짜
    private String todoDetail; // 투두 메모
    private Boolean todoCompleted; // 투두 성공 여부
}
