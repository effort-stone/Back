package com.effortstone.backend.domain.user.dto.response;

import com.effortstone.backend.domain.diary.dto.response.DiaryDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.todo.dto.response.TodoDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class CalenderResponseDto {
    // 각 날짜에 해당하는 루틴, 할일, 일기 목록
    private Map<LocalDate, List<RoutineDTO>> routines;
    private Map<LocalDate, List<TodoDto>> todos;
    private Map<LocalDate, List<DiaryDto>> diarys;
}
