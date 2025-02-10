package com.effortstone.backend.domain.common;

import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.routine.service.RoutineService;
import com.effortstone.backend.domain.stone.entity.Stone;
import com.effortstone.backend.domain.stone.repository.StoneRepository;
import com.effortstone.backend.domain.stone.service.StoneService;
import com.effortstone.backend.domain.stonewearableitme.entity.StoneWearableItem;
import com.effortstone.backend.domain.todo.dto.response.TodoDto;
import com.effortstone.backend.domain.todo.service.TodoService;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppInitialService {

    private final UserRepository userRepository;
    private final StoneService stoneService;
    private final RoutineService routineService;
    private final TodoService todoService;

    // 현재 앱 버전 상수 (프로퍼티로 관리할 수도 있음)
    private static final String CURRENT_APP_VERSION = "1.0.0";
    private final StoneRepository stoneRepository;

    public AppInitialResponseDto getAppInitialInfo(String clientVersion) {
        if (clientVersion == null) {
            clientVersion = "unknown";
        }
        // 현재 사용자 식별 (예: SecurityUtil)
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        // 사용자의 캐릭터(Stone) 조회
        Stone stone = stoneRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("사용자 캐릭터 정보를 찾을 수 없습니다."));;

        // 캐릭터가 착용한 아이템 중 장착된 아이템만 필터링
        List<StoneWearableItem> equippedItems = stone.getStoneWearableItems()
                .stream()
                .filter(StoneWearableItem::isStoneWearableItemEquipped)
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        List<RoutineDTO> todayRoutines = routineService.findTodayRoutines(today);
        List<TodoDto> todayTodos = todoService.findAllTodosForDate(today);

        // 응답 DTO 구성 (별도의 어셈블러 또는 빌더 패턴을 사용할 수도 있음)
        return AppInitialResponseDto.builder()
                .version(CURRENT_APP_VERSION)
                .clientVersion(clientVersion)
                .user(user)
                .character(stone)
                .equippedItems(equippedItems)
                .todayRoutine(todayRoutines)
                .todayTodos(todayTodos)
                .build();
    }
}
