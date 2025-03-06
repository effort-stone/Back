package com.effortstone.backend.domain.routine.dto.response;

import com.effortstone.backend.domain.routine.entity.RoutineGoalType;
import com.effortstone.backend.domain.routine.entity.RoutineTheme;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class RoutineDTO {
    private Long goalId; // routineCode;
    private String title; //routineName;
    private RoutineGoalType goalType ;//routineGoalType;
    private Integer targetTime; //routineFocusTime;
    private RoutineTheme goalTheme; // routineTheme;
    private String memo; //routineDetail;
    private LocalDate goalStartDate; //routineStartDate;
    private LocalDate goalEndDate; //routineEndDate;
    private List<Integer> repeatDays; //routineRepeatFrequency;
    @Schema(example = "15:33:22", type = "string")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime limitStartTime; //routineStartTime;
    @Schema(example = "15:33:22", type = "string")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime limitEndTime; //routineEndTime;
    @Schema(example = "15:33:22", type = "string")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime alramTime; //routineAlertTime;

    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime goalRegisterDate; // createdAt
}
