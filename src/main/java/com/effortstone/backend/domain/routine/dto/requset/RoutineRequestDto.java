package com.effortstone.backend.domain.routine.dto.requset;

import com.effortstone.backend.domain.routine.entity.RoutineGoalType;
import com.effortstone.backend.domain.routine.entity.RoutineTheme;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RoutineRequestDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RoutineCreateRequest {
        private String title;              // SQLite: title - Routine: routineName
        private String repeatDays;  // SQLite: repeatDays - Routine: routineRepeatFrequency
        private Integer goalType;  // SQLite: goalType - Routine: routineGoalType
        private Integer goalTheme;    // SQLite: goalTheme - Routine: routineTheme
        private Integer targetTime;        // SQLite: targetTime - Routine: routineFocusTime
        @Schema(example = "15:33", type = "string")
        @JsonFormat(pattern = "HH:mm")
        private LocalTime limitStartTime;  // SQLite: limitStartTime - Routine: routineStartTime
        @Schema(example = "15:33", type = "string")
        @JsonFormat(pattern = "HH:mm")
        private LocalTime limitEndTime;    // SQLite: limitEndTime - Routine: routineEndTime
        private LocalDate goalStartDate;   // SQLite: goalStartDate - Routine: routineStartDate
        private LocalDate goalEndDate;     // SQLite: goalEndDate - Routine: routineEndDate
        @Schema(example = "15:33", type = "string")
        @JsonFormat(pattern = "HH:mm")
        private LocalTime alramTime;       // SQLite: alramTime - Routine: routineAlertTime
        private String memo;               // SQLite: memo - Routine: routineDetail
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RoutineUpdateRequest {
        private Long goalId;               // SQLite: goalId - Routine: routineCode
        private String title;              // SQLite: title - Routine: routineName
        private String repeatDays;  // SQLite: repeatDays - Routine: routineRepeatFrequency
        private Integer goalType;  // SQLite: goalType - Routine: routineGoalType
        private Integer goalTheme;    // SQLite: goalTheme - Routine: routineTheme
        private Integer targetTime;        // SQLite: targetTime - Routine: routineFocusTime
        @Schema(example = "15:33", type = "string")
        @JsonFormat(pattern = "HH:mm")
        private LocalTime limitStartTime;  // SQLite: limitStartTime - Routine: routineStartTime
        @Schema(example = "15:33", type = "string")
        @JsonFormat(pattern = "HH:mm")
        private LocalTime limitEndTime;    // SQLite: limitEndTime - Routine: routineEndTime
        private LocalDate goalStartDate;   // SQLite: goalStartDate - Routine: routineStartDate
        private LocalDate goalEndDate;     // SQLite: goalEndDate - Routine: routineEndDate
        @Schema(example = "15:33", type = "string")
        @JsonFormat(pattern = "HH:mm")
        private LocalTime alramTime;       // SQLite: alramTime - Routine: routineAlertTime
        private String memo;               // SQLite: memo - Routine: routineDetail
        private Boolean isActive;
    }
}
