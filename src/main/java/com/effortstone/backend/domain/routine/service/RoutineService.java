package com.effortstone.backend.domain.routine.service;


import com.effortstone.backend.domain.routine.dto.requset.RoutineRequestDto;
import com.effortstone.backend.domain.routine.dto.response.CalendarResponseDTO;
import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.entity.RoutineGoalType;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.routine.entity.RoutineTheme;
import com.effortstone.backend.domain.routine.repository.RoutineProgressRepository;
import com.effortstone.backend.domain.routine.repository.RoutineRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final RoutineProgressRepository routineProgressRepository;

    private final UserRepository userRepository;

    // 🔹 모든 루틴 조회
    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    // 🔹 특정 루틴 조회
    public Routine getRoutineById(Long routineCode) {
        return routineRepository.findById(routineCode)
                .orElseThrow(() -> new RuntimeException("Routine not found"));
    }

    // 🔹 특정 사용자의 루틴 조회
    public List<Routine> getUserRoutines() {
        String userCode = SecurityUtil.getCurrentUserCode();
        return routineRepository.findByUser_UserCode(userCode);
    }


    // 🔹 루틴 생성 (Builder 적용)
    public RoutineDTO createRoutine(RoutineRequestDto.RoutineCreateRequest routine) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Routine newRoutine = Routine.builder()
                .user(user)
                .routineName(routine.getTitle())                  // title -> routineName
                .routineGoalType(RoutineGoalType.fromNumber(routine.getGoalType()))           // goalType -> routineGoalType
                .routineFocusTime(routine.getTargetTime())        // targetTime -> routineFocusTime
                .routineRepeatFrequency(parseRepeatDays(routine.getRepeatDays()))  // repeatDays -> routineRepeatFrequency
                .routineStartDate(routine.getGoalStartDate())     // goalStartDate -> routineStartDate
                .routineEndDate(routine.getGoalEndDate())         // goalEndDate -> routineEndDate
                .routineTheme(RoutineTheme.fromNumber(routine.getGoalTheme()))             // goalTheme -> routineTheme
                .routineDetail(routine.getMemo())                 // memo -> routineDetail
                .routineStartTime(routine.getLimitStartTime())    // limitStartTime -> routineStartTime
                .routineEndTime(routine.getLimitEndTime())        // limitEndTime -> routineEndTime
                .routineAlertTime(routine.getAlramTime())         // alramTime -> routineAlertTime
                .build();

        Routine savedRoutine = routineRepository.save(newRoutine);

        // Routine -> RoutineDTO 변환
        return convertToRoutineDTO(savedRoutine);
    }

    // 🔹 루틴 수정 (Builder 적용)
    public RoutineDTO updateRoutine(Long routineCode, RoutineRequestDto.RoutineUpdateRequest routineDetails) {
        String userCode = SecurityUtil.getCurrentUserCode();
        Routine updatedRoutine = getRoutineById(routineCode);

        // 🔹 사용자 검증 추가
        if (!updatedRoutine.getUser().getUserCode().equals(userCode)) {
            throw new RuntimeException("You do not have permission to update this routine.");
        }

        // Setter로 필드 업데이트
        updatedRoutine.setRoutineName(routineDetails.getTitle());
        updatedRoutine.setRoutineGoalType(RoutineGoalType.fromNumber(routineDetails.getGoalType()));
        updatedRoutine.setRoutineFocusTime(routineDetails.getTargetTime());
        updatedRoutine.setRoutineRepeatFrequency(parseRepeatDays(routineDetails.getRepeatDays()));
        updatedRoutine.setRoutineStartDate(routineDetails.getGoalStartDate());
        updatedRoutine.setRoutineEndDate(routineDetails.getGoalEndDate());
        updatedRoutine.setRoutineTheme(RoutineTheme.fromNumber(routineDetails.getGoalTheme()));
        updatedRoutine.setRoutineDetail(routineDetails.getMemo());
        updatedRoutine.setRoutineStartTime(routineDetails.getLimitStartTime());
        updatedRoutine.setRoutineEndTime(routineDetails.getLimitEndTime());
        updatedRoutine.setRoutineAlertTime(routineDetails.getAlramTime());
        updatedRoutine.setStatus(routineDetails.getIsActive());

        Routine savedRoutine = routineRepository.save(updatedRoutine);

        // Routine -> RoutineDTO 변환
        return convertToRoutineDTO(savedRoutine);
    }



    // 🔹 루틴 삭제
    public void deleteRoutine(Long routineCode) {
        Routine routine = getRoutineById(routineCode);
        String userCode = SecurityUtil.getCurrentUserCode();
        // 🔹 사용자 검증 추가
        if (!routine.getUser().getUserCode().equals(userCode)) {
            throw new RuntimeException("You do not have permission to delete this routine.");
        }
        routineRepository.delete(routine);
    }

//    /**
//     * 지정된 연월 동안, 해당 사용자의 루틴이 실행되어야 하는 날짜별 목록을 계산합니다.
//     * 각 날짜에 대해, 해당 루틴의 진행(완료) 여부도 함께 포함합니다.
//     */
//    public CalendarResponseDTO getMonthlyCalendar(YearMonth yearMonth) {
//        // 1) 사용자 조회
//        String userCode = SecurityUtil.getCurrentUserCode();
//        User currentUser = userRepository.findById(userCode)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        // 2) 해당 사용자의 모든 루틴 조회
//        List<Routine> routines = routineRepository.findByUser(currentUser);
//
//        // 3) 조회할 달의 시작일과 종료일
//        LocalDate monthStart = yearMonth.atDay(1);
//        LocalDate monthEnd = yearMonth.atEndOfMonth();
//
//        // 4) 달력 맵 초기화: 날짜별 루틴리스트
//        Map<LocalDate, List<RoutineDTO>> dailyRoutines = new HashMap<>();
//        for (LocalDate date = monthStart; !date.isAfter(monthEnd); date = date.plusDays(1)) {
//            dailyRoutines.put(date, new ArrayList<>());
//        }
//
//        // 5) 해당 달 동안, 조회된 루틴들의 진행 내역을 한 번에 조회
//        //    (루틴Progress는 각 루틴에 대해 하루에 한 건 있다고 가정)
//        List<RoutineProgress> progressList = routineProgressRepository
//                .findByRoutineInAndRoutineProgressDateBetween(routines, monthStart, monthEnd);
//        // progressMap: key = routineCode, value = (key: date, value: RoutineProgress)
//        Map<Long, Map<LocalDate, RoutineProgress>> progressMap = new HashMap<>();
//        for (RoutineProgress rp : progressList) {
//            Long routineId = rp.getRoutine().getRoutineCode();
//            progressMap.computeIfAbsent(routineId, k -> new HashMap<>())
//                    .put(rp.getRoutineProgressDate(), rp);
//        }
//
//        // 6) 각 루틴에 대해 해당 월에 표시될 날짜를 계산하고, 진행 여부 포함
//        for (Routine routine : routines) {
//            LocalDate routineStart = routine.getRoutineStartDate();
//            LocalDate routineEnd = (routine.getRoutineEndDate() != null)
//                    ? routine.getRoutineEndDate()
//                    : monthEnd; // 종료일이 null이면 해당 달 말일로 처리
//
//            // 루틴 기간과 조회 월이 겹치지 않으면 패스
//            if (routineStart.isAfter(monthEnd) || routineEnd.isBefore(monthStart)) {
//                continue;
//            }
//
//            // 반복 요일: 프론트에서 0=월, 6=일로 저장했다고 가정 (매핑: dayIdx + 1)
//            List<Integer> routineRepeatFrequency = routine.getRoutineRepeatFrequency();
//            Set<DayOfWeek> repeatDays = new HashSet<>();
//            if (routineRepeatFrequency != null && !routineRepeatFrequency.isEmpty()) {
//                for (Integer dayIdx : routineRepeatFrequency) {
//                    int dayOfWeekValue = dayIdx + 1; // 0 -> 1(MONDAY), 6 -> 7(SUNDAY)
//                    if (dayOfWeekValue >= 1 && dayOfWeekValue <= 7) {
//                        repeatDays.add(DayOfWeek.of(dayOfWeekValue));
//                    }
//                }
//            } else {
//                // 반복 요일 정보가 없으면 매일 실행으로 간주
//                repeatDays = EnumSet.allOf(DayOfWeek.class);
//            }
//
//            // 지정 월의 각 날짜에 대해 해당 루틴이 실행되어야 하면,
//            // 해당 날짜에 있는 진행 내역(RoutineProgress)이 있는지 확인하여 완료 여부를 결정
//            for (LocalDate date = monthStart; !date.isAfter(monthEnd); date = date.plusDays(1)) {
//                if (!date.isBefore(routineStart) && !date.isAfter(routineEnd)) {
//                    if (repeatDays.contains(date.getDayOfWeek())) {
//                        // 해당 날짜에 대한 진행 내역 조회
//                        RoutineProgress progress = null;
//                        Map<LocalDate, RoutineProgress> routineProgressForRoutine = progressMap.get(routine.getRoutineCode());
//                        if (routineProgressForRoutine != null) {
//                            progress = routineProgressForRoutine.get(date);
//                        }
//                        // mapToDTO 오버로드: Routine과 (옵션으로) 진행 내역 정보를 이용하여 DTO 생성
//                        RoutineDTO routineDTO = mapToDTO(routine, progress);
//                        dailyRoutines.get(date).add(routineDTO);
//                    }
//                }
//            }
//        }
//
//        // 7) 결과 CalendarResponseDTO로 반환
//        return new CalendarResponseDTO(dailyRoutines);
//    }

//    /**
//     * 특정 날짜(date)에 해당 사용자가 해야 할 "오늘의 루틴" 목록을 조회한다.
//     */
//    public List<RoutineDTO> findTodayRoutines(LocalDate today) {
//        String userCode = SecurityUtil.getCurrentUserCode();
//        User user = userRepository.findById(userCode)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        // 1) 해당 사용자의 모든 루틴 조회
//        List<Routine> allRoutines = routineRepository.findByUser(user);
//
//        // 2) 오늘 날짜(today)에 실행되어야 하는 루틴만 필터링
//        List<Routine> activeRoutines = allRoutines.stream()
//                .filter(routine -> isRoutineActiveOnDate(routine, today))
//                .collect(Collectors.toList());
//
//        // 3) 오늘 날짜에 해당하는 진행 내역들을 한 번에 조회
//        List<RoutineProgress> progressList = routineProgressRepository
//                .findByRoutineInAndRoutineProgressDateBetween(activeRoutines, today, today);
//
//        // progressMap: key = routineCode, value = 해당 날짜의 진행 내역 (있으면 한 건, 없으면 null)
//        Map<Long, RoutineProgress> progressMap = progressList.stream()
//                .collect(Collectors.toMap(
//                        rp -> rp.getRoutine().getRoutineCode(),
//                        rp -> rp,
//                        (existing, replacement) -> existing  // 동일 루틴에 대해 여러 건이면 기존값 사용
//                ));
//
//        // 4) 각 루틴을 DTO로 변환하며, 오늘의 진행 내역이 있는지에 따라 완료 여부 설정
//        List<RoutineDTO> todayRoutines = activeRoutines.stream()
//                .map(routine -> {
//                    RoutineProgress progress = progressMap.get(routine.getRoutineCode());
//                    // 진행 내역이 없으면 false, 있으면 progress의 완료 여부를 사용
//                    return mapToDTO(routine, progress);
//                })
//                .collect(Collectors.toList());
//
//        return todayRoutines;
//    }

    /**
     * 해당 루틴이 특정 날짜(date)에 실행되는지 여부를 체크하는 메서드
     */
    private boolean isRoutineActiveOnDate(Routine routine, LocalDate date) {
        // (1) 날짜 범위 체크: 시작일~종료일 범위 안에 있는가?
        LocalDate start = routine.getRoutineStartDate();
        LocalDate end = routine.getRoutineEndDate() != null
                ? routine.getRoutineEndDate()
                : LocalDate.now().plusYears(10);
        // 종료일이 null이면 임시로 매우 먼 미래로 설정

        boolean inRange = !date.isBefore(start) && !date.isAfter(end);
        if (!inRange) {
            return false;
        }
        // (2) 요일 체크
        //     routineRepeatFrequency가 비어있으면 "매일"로 간주하거나,
        //     혹은 "반복 요일이 설정되지 않으면 실행 안 함"으로 처리할 수도 있다. (팀 규칙에 따라)
        List<Integer> freq = routine.getRoutineRepeatFrequency();
        if (freq == null || freq.isEmpty()) {
            // 반복 요일이 비어있으면 "매일"이라고 가정 (또는 false로 처리)
            return true;
        } else {
            // 자바 DayOfWeek: Monday=1, Sunday=7
            // 예: 월(1) - 1 => 0
            int dayIndex = date.getDayOfWeek().getValue() - 1;
            return freq.contains(dayIndex);
        }
    }

//    /**
//     * Routine 엔티티를 RoutineDTO로 변환
//     */
//    private RoutineDTO mapToDTO(Routine routine, RoutineProgress progress) {
//        return RoutineDTO.builder()
//                .routineCode(routine.getRoutineCode())
//                .routineName(routine.getRoutineName())
//                .routineGoalType(routine.getRoutineGoalType().name())
//                .routineFocusTime(routine.getRoutineFocusTime())
//                .routineTheme(routine.getRoutineTheme())
//                .routineDetail(routine.getRoutineDetail())
//                .routineStartDate(routine.getRoutineStartDate())
//                .routineEndDate(routine.getRoutineEndDate())
//                .routineRepeatFrequency(routine.getRoutineRepeatFrequency())
//                .routineStartTime(routine.getRoutineStartTime())
//                .routineEndTime(routine.getRoutineEndTime())
//                .routineAlertTime(routine.getRoutineAlertTime())
//                .routineProgressCompleted(progress != null ? progress.getRoutineProgressCompleted() : false)
//                .build();
//    }

    // 🔹 Routine을 RoutineDTO로 변환하는 헬퍼 메서드
    private RoutineDTO convertToRoutineDTO(Routine routine) {
        return RoutineDTO.builder()
                .goalId(routine.getRoutineCode())
                .title(routine.getRoutineName())
                .goalType(routine.getRoutineGoalType().getNumber())       // String으로 가정, Enum이면 .name() 추가
                .targetTime(routine.getRoutineFocusTime())
                .goalTheme(routine.getRoutineTheme().getNumber())
                .memo(routine.getRoutineDetail())
                .goalStartDate(routine.getRoutineStartDate())
                .goalEndDate(routine.getRoutineEndDate())
                .repeatDays(paresStringRepeatDays(routine.getRoutineRepeatFrequency()))
                .limitStartTime(routine.getRoutineStartTime())
                .limitEndTime(routine.getRoutineEndTime())
                .alramTime(routine.getRoutineAlertTime())
                .goalRegisterDate(routine.getCreatedAt())     // createdAt 필드 가정
                .isActive(routine.getStatus())
                .build();
    }

    // 저장할 때 "0,1,2" → [0,1,2] (List<Integer>)
    public static List<Integer> parseRepeatDays(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(json.split(",")) // "0,1,2" → ["0", "1", "2"]
                .map(Integer::parseInt)      // ["0", "1", "2"] → [0, 1, 2]
                .collect(Collectors.toList());
    }

    // 반환할때 [0,1,2] → "0,1,2"  (String)
    public static String paresStringRepeatDays(List<Integer> listrepeatdays) {
        if (listrepeatdays == null || listrepeatdays.isEmpty()) {
            return "";
        }
        return listrepeatdays
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }


}
