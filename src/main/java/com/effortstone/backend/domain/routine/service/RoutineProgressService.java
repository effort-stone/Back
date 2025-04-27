package com.effortstone.backend.domain.routine.service;

import com.effortstone.backend.domain.routine.dto.requset.RoutineProgressRequestDto;
import com.effortstone.backend.domain.routine.dto.requset.RoutineProgressUpdateRequestDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressResponseDto;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.routine.repository.RoutineProgressRepository;
import com.effortstone.backend.domain.routine.repository.RoutineRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineProgressService {

    private final RoutineProgressRepository routineProgressRepository;
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

//    /**
//     * 월간 캘린더 데이터 조회
//     * 해당 사용자의 지정된 연월에 해당하는 모든 루틴 진행 데이터를 조회하여,
//     * 날짜별로 그룹핑한 후 DTO로 변환하여 반환합니다.
//     */
//    public Map<LocalDate, List<RoutineProgressDTO>> getMonthlyCalendarData(User user, YearMonth yearMonth) {
//        LocalDate startDate = yearMonth.atDay(1);
//        LocalDate endDate = yearMonth.atEndOfMonth();
//
//        List<RoutineProgress> progressList =
//                routineProgressRepository.findByRoutineUserAndRoutineProgressDateBetween(user, startDate, endDate);
//
//        Map<LocalDate, List<RoutineProgressDTO>> calendarData = new HashMap<>();
//        for (RoutineProgress progress : progressList) {
//            LocalDate date = progress.getRoutineProgressDate();
//            calendarData.computeIfAbsent(date, d -> new ArrayList<>()).add(mapToDTO(progress));
//        }
//        return calendarData;
//    }

    /**
     * 루틴 실행 완료/업데이트 처리
     * 해당 루틴과 날짜에 대한 진행 기록을 저장하거나 업데이트합니다.
     * 체크형이면 완료 시각을 기록하고, 시간 기록형이면 소요 시간을 기록합니다.
     */
    @Transactional
    public RoutineProgressResponseDto recordRoutineProgress(RoutineProgressRequestDto dto) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Routine routine = routineRepository.findById(dto.getGoalId())
                .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));;
        // 동일 루틴/날짜에 대한 진행 기록이 있는지 확인
        // ✅ 올바른 단건 조회 사용
        Optional<RoutineProgress> optionalProgress = routineProgressRepository
                .findByRoutineAndRoutineProgressCompletionTime(routine, dto.getRecordTime());
        RoutineProgress progress;
        if (optionalProgress.isPresent()) {
            // 기존 기록이 있으면 업데이트
            progress = optionalProgress.get();
            progress.setRoutineProgressCompleted(dto.getIsAchieved());
            progress.setRoutineProgressCompletionTime(dto.getRecordTime());
            progress.setRoutineProgressRecordedAmount(dto.getCurrentEffortTime());
        } else {
            // 없으면 새로 생성 (RoutineProgress 엔티티의 Builder 또는 생성자 사용)
            progress = RoutineProgress.builder()
                    .routine(routine)
                    .routineProgressCompleted(dto.getIsAchieved())
                    .routineProgressCompletionTime(dto.getRecordTime())
                    .routineProgressRecordedAmount(dto.getCurrentEffortTime())
                    .build();
        }
        routineProgressRepository.save(progress);

        return mapToDTO(progress);
    }

    /**
     * 루틴 실행 업데이트 처리
     * 해당 루틴과 날짜에 대한 진행 기록을 업데이트합니다.
     * 체크형이면 완료 시각을 기록하고, 시간 기록형이면 소요 시간을 기록합니다.
     */
    @Transactional
    public ApiResponse<RoutineProgressResponseDto> recordUpdateRoutineProgress(RoutineProgressUpdateRequestDto dto) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Routine routine = routineRepository.findById(dto.getGoalId())
                .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));;
        // 동일 루틴/날짜에 대한 진행 기록이 있는지 확인
        // ✅ 단건 조회 사용
        RoutineProgress optionalProgress = routineProgressRepository
                .findByRoutineAndRoutineProgressCompletionTime(routine, dto.getRecordTime())
                .orElseThrow(() -> new RuntimeException("루틴 진행 기록을 찾을 수 없습니다.")); // 고쳤음
        optionalProgress.setRoutineProgressCompleted(dto.getIsAchieved());
        optionalProgress.setRoutineProgressCompletionTime(dto.getRecordTime());
        routineProgressRepository.save(optionalProgress);

        return ApiResponse.success(SuccessCode.ROUTINE_PROGRESS_UPDATE_SUCCESS, mapToDTO(optionalProgress));
    }

    /**
     * 루틴 진행 기록 취소
     * 해당 날짜의 진행 기록을 삭제하거나, 상태를 초기화합니다.
     *
     * @return
     */
    @Transactional
    public ApiResponse<Boolean> deleteRoutineProgress(Long routine_progress_code) {
        Optional<RoutineProgress> optionalProgress = routineProgressRepository.findById(routine_progress_code);
        if (optionalProgress.isPresent()) {
            RoutineProgress progress = optionalProgress.get();
            routineProgressRepository.delete(progress);
        }
        return ApiResponse.success(SuccessCode.ROUTINE_PROGRESS_DELETE_SUCCESS,Boolean.TRUE);
    }

    /**
     * 엔티티를 DTO로 변환
     * (필요한 필드만 전달)
     */
    private RoutineProgressResponseDto mapToDTO(RoutineProgress progress) {
        return RoutineProgressResponseDto.builder()
                .goalId(progress.getRoutine().getRoutineCode())
                .recordId(progress.getRoutineProgressCode())
                .isAchieved(progress.getRoutineProgressCompleted())
                .currentEffortTime(progress.getRoutineProgressRecordedAmount() != null ? progress.getRoutineProgressRecordedAmount() : null)
                .recordTime(progress.getRoutineProgressCompletionTime())
                .build();
    }
}
