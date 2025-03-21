package com.effortstone.backend.domain.routine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class CalendarResponseDTO {
    // 날짜별로 실행되어야 하는 루틴 목록을 전달 (예: "2025-04-29" → [루틴DTO, ...])
    private Map<LocalDate, List<RoutineResponseDto>> dailyRoutines;
}