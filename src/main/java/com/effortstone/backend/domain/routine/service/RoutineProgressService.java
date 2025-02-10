package com.effortstone.backend.domain.routine.service;

import com.effortstone.backend.domain.routine.dto.requset.RoutineProgressRequestDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressDTO;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.routine.repository.RoutineProgressRepository;
import com.effortstone.backend.domain.routine.repository.RoutineRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineProgressService {

    private final RoutineProgressRepository routineProgressRepository;
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    /**
     * 월간 캘린더 데이터 조회
     * 해당 사용자의 지정된 연월에 해당하는 모든 루틴 진행 데이터를 조회하여,
     * 날짜별로 그룹핑한 후 DTO로 변환하여 반환합니다.
     */
    public Map<LocalDate, List<RoutineProgressDTO>> getMonthlyCalendarData(User user, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<RoutineProgress> progressList =
                routineProgressRepository.findByRoutineUserAndRoutineProgressDateBetween(user, startDate, endDate);

        Map<LocalDate, List<RoutineProgressDTO>> calendarData = new HashMap<>();
        for (RoutineProgress progress : progressList) {
            LocalDate date = progress.getRoutineProgressDate();
            calendarData.computeIfAbsent(date, d -> new ArrayList<>()).add(mapToDTO(progress));
        }
        return calendarData;
    }

    /**
     * 루틴 실행 완료/업데이트 처리
     * 해당 루틴과 날짜에 대한 진행 기록을 저장하거나 업데이트합니다.
     * 체크형이면 완료 시각을 기록하고, 시간 기록형이면 소요 시간을 기록합니다.
     */
    @Transactional
    public RoutineProgressDTO recordRoutineProgress(RoutineProgressRequestDto dto) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Routine routine = routineRepository.findById(dto.getRoutineCode())
                .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));;
        // 동일 루틴/날짜에 대한 진행 기록이 있는지 확인
        // ✅ 올바른 단건 조회 사용
        Optional<RoutineProgress> optionalProgress = routineProgressRepository
                .findByRoutineAndRoutineProgressDate(routine, dto.getProgressDate());
        RoutineProgress progress;
        if (optionalProgress.isPresent()) {
            // 기존 기록이 있으면 업데이트
            progress = optionalProgress.get();
            progress.setRoutineProgressDate(dto.getProgressDate());
            progress.setRoutineProgressCompleted(dto.getCompleted());
            progress.setRoutineProgressCompletionTime(dto.getCompletionTime());
            progress.setRoutineProgressRecordedAmount(dto.getRecordedAmount());
        } else {
            // 없으면 새로 생성 (RoutineProgress 엔티티의 Builder 또는 생성자 사용)
            progress = RoutineProgress.builder()
                    .routine(routine)
                    .routineProgressDate(dto.getProgressDate())
                    .routineProgressCompleted(dto.getCompleted())
                    .routineProgressCompletionTime(dto.getCompletionTime())
                    .routineProgressRecordedAmount(dto.getRecordedAmount())
                    .build();
        }
        routineProgressRepository.save(progress);

        return mapToDTO(progress);
    }

    /**
     * 루틴 진행 기록 취소
     * 해당 날짜의 진행 기록을 삭제하거나, 상태를 초기화합니다.
     */
    @Transactional
    public void cancelRoutineProgress(User user, Routine routine, LocalDate progressDate) {
        Optional<RoutineProgress> optionalProgress = routineProgressRepository.findByRoutineUserAndRoutineProgressDateBetween(
                user, progressDate, progressDate).stream().filter(p -> p.getRoutine().equals(routine)).findFirst();
        if (optionalProgress.isPresent()) {
            RoutineProgress progress = optionalProgress.get();
            // 상황에 따라 삭제하거나, 상태만 업데이트할 수 있습니다.
            // 여기서는 삭제 예시를 들겠습니다.
            routineProgressRepository.delete(progress);
        }
    }

    /**
     * 엔티티를 DTO로 변환
     * (필요한 필드만 전달)
     */
    private RoutineProgressDTO mapToDTO(RoutineProgress progress) {
        return RoutineProgressDTO.builder()
                .routine(progress.getRoutine())
                .completed(progress.getRoutineProgressCompleted())
                .completionTime(progress.getRoutineProgressCompletionTime() != null ? progress.getRoutineProgressCompletionTime() : null)
                .recordedAmount(progress.getRoutineProgressRecordedAmount())
                .build();
    }
}
