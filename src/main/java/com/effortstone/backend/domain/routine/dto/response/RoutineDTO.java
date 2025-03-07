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
}
