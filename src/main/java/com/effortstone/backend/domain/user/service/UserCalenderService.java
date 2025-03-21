package com.effortstone.backend.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCalenderService {

//    private final RoutineService routineCalendarService;
//    private final TodoService todoCalendarService;
//    private final DiaryService diaryCalendarService;
//    private final UserRepository userRepository;
//
//    public CalenderResponseDto getCalendarData(YearMonth yearMonth) {
//        String userCode = SecurityUtil.getCurrentUserCode();
//        User user = userRepository.findById(userCode)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Map<LocalDate, List<RoutineDTO>> routines =
//                routineCalendarService.getMonthlyCalendar(yearMonth).getDailyRoutines();
//        Map<LocalDate, List<TodoDto>> todos =
//                todoCalendarService.getMonthlyTodos(yearMonth);
//        Map<LocalDate, List<DiaryDto>> diarys =
//                diaryCalendarService.getMonthlyDiarys(yearMonth).getDailyDiaries();
//
//        CalenderResponseDto responseDto = CalenderResponseDto.builder()
//                .routines(routines)
//                .todos(todos)
//                .diarys(diarys)
//                .build();
//
//        return responseDto;
//    }

}
