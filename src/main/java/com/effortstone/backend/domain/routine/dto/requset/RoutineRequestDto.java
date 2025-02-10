package com.effortstone.backend.domain.routine.dto.requset;

import com.effortstone.backend.domain.routine.entity.RoutineGoalType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RoutineRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class RoutineCreateRequest {
        private String routineName;
        private RoutineGoalType routineGoalType;
        private LocalTime routineFocusTime;
        private List<Integer> routineRepeatFrequency;
        private LocalDate routineStartDate;
        private LocalDate routineEndDate;
        private String routineTheme;
        private String routineDetail;
        private LocalTime routineStartTime;
        private LocalTime routineEndTime;
        private LocalTime routineAlertTime;
    }
}
