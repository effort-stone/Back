package com.effortstone.backend.domain.routine.dto.response;

import com.effortstone.backend.domain.routine.entity.Routine;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class RoutineProgressDTO {
    private Long goalId; // routineCode;
    private Long recordId; // 루틴내역 코드 routineProgressCode
    private Boolean isAchieved;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime recordTime; //completionTime;
    private Integer currentEffortTime;  // routineProgressRecordedAmount
    //private boolean status; // status
}
