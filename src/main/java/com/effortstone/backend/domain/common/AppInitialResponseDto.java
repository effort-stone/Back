package com.effortstone.backend.domain.common;

import com.effortstone.backend.domain.routine.dto.response.RoutineResponseDto;
import com.effortstone.backend.domain.todo.dto.response.TodoResponseDto;
import com.effortstone.backend.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppInitialResponseDto {
    private String version;       // 서버 버전
    private String clientVersion; // 클라이언트 버전
    private User user;            // 사용자 정보
    private List<RoutineResponseDto> todayRoutine;         // 오늘의 루틴
    private List<TodoResponseDto> todayTodos;                      // 오늘의 TODO 목록
}
