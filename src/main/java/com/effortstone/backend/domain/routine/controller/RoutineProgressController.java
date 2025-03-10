package com.effortstone.backend.domain.routine.controller;

import com.effortstone.backend.domain.routine.dto.requset.RoutineProgressRequestDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressDTO;
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
    public ResponseEntity<ApiResponse<RoutineProgressDTO>> createRoutineProgress(@RequestBody RoutineProgressRequestDto routine) {
        RoutineProgressDTO createdRoutine = routineProgressService.recordRoutineProgress(routine);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_CREATE_SUCCESS, createdRoutine));
    }
    // 🔹 루틴상세 수정
    @PutMapping("/{routine_progress_code}")
    public ApiResponse<RoutineProgressDTO> updateRoutineProgress(@RequestBody RoutineProgressRequestDto routine, @PathVariable Long routine_progress_code) {
        return routineProgressService.recordUpdateRoutineProgress(routine, routine_progress_code);
    }
}
