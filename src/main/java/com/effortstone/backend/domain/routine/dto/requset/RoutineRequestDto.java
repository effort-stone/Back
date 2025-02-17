package com.effortstone.backend.domain.routine.dto.requset;

import com.effortstone.backend.domain.routine.entity.RoutineGoalType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(example = "08:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime routineFocusTime;
        private List<Integer> routineRepeatFrequency;
        private LocalDate routineStartDate;
        private LocalDate routineEndDate;
        private String routineTheme;
        private String routineDetail;
        @Schema(example = "08:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime routineStartTime;
        @Schema(example = "08:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime routineEndTime;
        @Schema(example = "08:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime routineAlertTime;
    }
}
