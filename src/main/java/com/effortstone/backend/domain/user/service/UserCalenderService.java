package com.effortstone.backend.domain.user.service;

import com.effortstone.backend.domain.diary.dto.response.DiaryDto;
import com.effortstone.backend.domain.diary.service.DiaryService;
import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.routine.service.RoutineService;
import com.effortstone.backend.domain.todo.dto.response.TodoDto;
import com.effortstone.backend.domain.todo.service.TodoService;
import com.effortstone.backend.domain.user.dto.response.CalenderResponseDto;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserCalenderService {

    private final RoutineService routineCalendarService;
    private final TodoService todoCalendarService;
    private final DiaryService diaryCalendarService;
    private final UserRepository userRepository;

    public CalenderResponseDto getCalendarData(YearMonth yearMonth) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<LocalDate, List<RoutineDTO>> routines =
                routineCalendarService.getMonthlyCalendar(yearMonth).getDailyRoutines();
        Map<LocalDate, List<TodoDto>> todos =
                todoCalendarService.getMonthlyTodos(yearMonth);
        Map<LocalDate, List<DiaryDto>> diarys =
                diaryCalendarService.getMonthlyDiarys(yearMonth).getDailyDiaries();

        CalenderResponseDto responseDto = CalenderResponseDto.builder()
                .routines(routines)
                .todos(todos)
                .diarys(diarys)
                .build();

        return responseDto;
    }

}
