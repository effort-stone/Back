package com.effortstone.backend.domain.routine.controller;


import com.effortstone.backend.domain.routine.dto.requset.RoutineRequestDto;
import com.effortstone.backend.domain.routine.dto.response.CalendarResponseDTO;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.service.RoutineService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/routines")
public class RoutineController {
    private final RoutineService routineService;

    // 🔹 모든 루틴 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Routine>>> getAllRoutines() {
        List<Routine> routines = routineService.getAllRoutines();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_LIST_FETCH_SUCCESS, routines));
    }

    // 🔹 특정 루틴 조회
    @GetMapping("/{routineCode}")
    public ResponseEntity<ApiResponse<Routine>> getRoutine(@PathVariable Long routineCode) {
        Routine routine = routineService.getRoutineById(routineCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_FETCH_SUCCESS, routine));
    }

    // 🔹 내 루틴 목록 조회 (Firebase 토큰 기반)
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<Routine>>> getUserRoutines() {
        List<Routine> userRoutines = routineService.getUserRoutines();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_USER_LIST_FETCH_SUCCESS, userRoutines));
    }

    // 🔹 루틴 생성 (Firebase 토큰 기반)
    @PostMapping
    public ResponseEntity<ApiResponse<Routine>> createRoutine(@RequestBody RoutineRequestDto.RoutineCreateRequest routine) {
        Routine createdRoutine = routineService.createRoutine(routine);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_CREATE_SUCCESS, createdRoutine));
    }

    // 🔹 루틴 수정
    @PutMapping("/{routineCode}")
    public ResponseEntity<ApiResponse<Routine>> updateRoutine(
            @PathVariable Long routineCode,
            @RequestBody Routine routineDetails) {
        Routine updatedRoutine = routineService.updateRoutine(routineCode, routineDetails);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_UPDATE_SUCCESS, updatedRoutine));
    }

    // 🔹 루틴 삭제
    @DeleteMapping("/{routineCode}")
    public ResponseEntity<ApiResponse<Void>> deleteRoutine(@PathVariable Long routineCode) {
        routineService.deleteRoutine(routineCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_DELETE_SUCCESS));
    }

    /**
     * 월간 캘린더 데이터 조회 API
     * URL 예시: GET /api/v1/routines/calendar?year=2025&month=4
     */
    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<CalendarResponseDTO>> getCalendar(
            @RequestParam int year,
            @RequestParam int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        CalendarResponseDTO responseDTO = routineService.getMonthlyCalendar(yearMonth);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_CALENDAR_FETCH_SUCCESS, responseDTO));
    }

}
