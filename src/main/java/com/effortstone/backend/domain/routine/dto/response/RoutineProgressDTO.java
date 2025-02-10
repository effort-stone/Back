package com.effortstone.backend.domain.routine.dto.response;

import com.effortstone.backend.domain.routine.entity.Routine;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class RoutineProgressDTO {
    private Routine routine;
    private String routineName;
    private Boolean completed;
    private LocalDateTime completionTime; // 체크형일 경우
    private Integer recordedAmount;   // 시간 기록형일 경우(분 단위 등)
}
