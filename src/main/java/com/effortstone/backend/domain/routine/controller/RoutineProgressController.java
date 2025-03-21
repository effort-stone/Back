package com.effortstone.backend.domain.routine.controller;

import com.effortstone.backend.domain.routine.dto.requset.RoutineProgressRequestDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressResponseDto;
import com.effortstone.backend.domain.routine.service.RoutineProgressService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routines/progress")
public class RoutineProgressController {
    private final RoutineProgressService routineProgressService;

    // 🔹 루틴상세 생성
    @PostMapping("/")
    public ResponseEntity<ApiResponse<RoutineProgressResponseDto>> createRoutineProgress(@RequestBody RoutineProgressRequestDto routine) {
        RoutineProgressResponseDto createdRoutine = routineProgressService.recordRoutineProgress(routine);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_CREATE_SUCCESS, createdRoutine));
    }
    // 🔹 루틴상세 수정
    @PutMapping("")
    public ApiResponse<RoutineProgressResponseDto> updateRoutineProgress(@RequestBody RoutineProgressRequestDto routine) {
        return routineProgressService.recordUpdateRoutineProgress(routine);
    }

    // 🔹 루틴상세 삭제
    @DeleteMapping("/{routine_progress_code}")
    public ApiResponse<Boolean> deleteRoutineProgress(@PathVariable Long routine_progress_code) {
        return routineProgressService.deleteRoutineProgress(routine_progress_code);
    }
}
