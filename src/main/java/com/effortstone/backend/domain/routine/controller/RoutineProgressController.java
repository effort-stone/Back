package com.effortstone.backend.domain.routine.controller;

import com.effortstone.backend.domain.routine.dto.requset.RoutineProgressRequestDto;
import com.effortstone.backend.domain.routine.dto.requset.RoutineRequestDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressDTO;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.service.RoutineProgressService;
import com.effortstone.backend.domain.routine.service.RoutineService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routines/progress")
public class RoutineProgressController {
    private final RoutineProgressService routineProgressService;

    // 🔹 루틴상세 생성
    @PostMapping
    public ResponseEntity<ApiResponse<RoutineProgressDTO>> createRoutine(@RequestBody RoutineProgressRequestDto routine) {
        RoutineProgressDTO createdRoutine = routineProgressService.recordRoutineProgress(routine);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ROUTINE_CREATE_SUCCESS, createdRoutine));
    }
}
