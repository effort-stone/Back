package com.effortstone.backend.domain.routine.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class RoutineDTO {
    private Long routineCode;
    private String routineName;
    private String routineGoalType;
    private LocalTime routineFocusTime;
    private String routineTheme;
    private String routineDetail;
    private LocalDate routineStartDate;
    private LocalDate routineEndDate;
    private List<Integer> routineRepeatFrequency;
    private LocalTime routineStartTime;
    private LocalTime routineEndTime;
    private LocalTime routineAlertTime;
    private Boolean routineProgressCompleted; // 해당 날짜에 루틴이 완료되었는지 여부
}