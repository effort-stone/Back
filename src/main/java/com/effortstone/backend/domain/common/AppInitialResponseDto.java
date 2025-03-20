package com.effortstone.backend.domain.common;

import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.todo.dto.response.TodoDto;
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
    private List<RoutineDTO> todayRoutine;         // 오늘의 루틴
    private List<TodoDto> todayTodos;                      // 오늘의 TODO 목록
}
