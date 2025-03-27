package com.effortstone.backend.domain.routine.dto.response;

import com.effortstone.backend.domain.routine.entity.Routine;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.effortstone.backend.domain.routine.service.RoutineService.paresStringRepeatDays;

@Data
@Builder
public class RoutineResponseDto {
    private Long goalId; // routineCode;
    private String title; //routineName;
    private Integer goalType ;//routineGoalType;
    private Integer targetTime; //routineFocusTime;
    private Integer goalTheme; // routineTheme;
    private String memo; //routineDetail;
    private LocalDate goalStartDate; //routineStartDate;
    private LocalDate goalEndDate; //routineEndDate;
    private String repeatDays; //routineRepeatFrequency;
    @Schema(example = "15:33", type = "string")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime limitStartTime; //routineStartTime;
    @Schema(example = "15:33", type = "string")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime limitEndTime; //routineEndTime;
    @Schema(example = "15:33", type = "string")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime alramTime; //routineAlertTime;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime goalRegisterDate; // createdAt
    private Boolean isActive;

    // 🔹 Routine을 RoutineDTO로 변환하는 헬퍼 메서드
    public static RoutineResponseDto fromEntity(Routine routine) {
        return RoutineResponseDto.builder()
                .goalId(routine.getRoutineCode())
                .title(routine.getRoutineName())
                .goalType(routine.getRoutineGoalType().getNumber())       // String으로 가정, Enum이면 .name() 추가
                .targetTime(routine.getRoutineFocusTime())
                .goalTheme(routine.getRoutineTheme().getNumber())
                .memo(routine.getRoutineDetail())
                .goalStartDate(routine.getRoutineStartDate())
                .goalEndDate(routine.getRoutineEndDate())
                .repeatDays(paresStringRepeatDays(routine.getRoutineRepeatFrequency()))
                .limitStartTime(routine.getRoutineStartTime())
                .limitEndTime(routine.getRoutineEndTime())
                .alramTime(routine.getRoutineAlertTime())
                .goalRegisterDate(routine.getCreatedAt())     // createdAt 필드 가정
                .isActive(routine.getStatus())
                .build();
    }

}
