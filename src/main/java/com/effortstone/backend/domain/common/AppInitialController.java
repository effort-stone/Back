package com.effortstone.backend.domain.common;

import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.service.RoutineService;
import com.effortstone.backend.domain.stone.entity.Stone;
import com.effortstone.backend.domain.stone.service.StoneService;
import com.effortstone.backend.domain.stonewearableitme.entity.StoneWearableItem;
import com.effortstone.backend.domain.todo.dto.response.TodoDto;
import com.effortstone.backend.domain.todo.entity.Todo;
import com.effortstone.backend.domain.todo.service.TodoService;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.domain.user.service.UserService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.effortstone.backend.global.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppInitialController {
    private final UserService userService;
    private final RoutineService routineService;
    private final TodoService todoService;

    // 서버가 지원하는 현재 버전 (간단 예시)
    private static final String CURRENT_APP_VERSION = "1.2.0";
    private final UserRepository userRepository;
    private final StoneService stoneService;
    private final AppInitialService appInitialService;

    /**
     * 앱 초기 로딩 API
     * - 사용자 버전 체크
     * - 유저 정보 / 오늘 루틴 / TODO 리스트 응답
     * - 캐릭터 정보는 주석 처리
     */
    @GetMapping("/initial")
    public ResponseEntity<ApiResponse<AppInitialResponseDto>> getAppInitialInfo(
            @RequestHeader(value = "X-App-Version", required = false) String clientVersion
    ) {
        AppInitialResponseDto responseDto = appInitialService.getAppInitialInfo(clientVersion);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.APP_INITIAL_FETCH_SUCCESS, responseDto));
    }
}
