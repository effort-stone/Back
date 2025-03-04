package com.effortstone.backend.domain.routine.repository;

import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineProgressRepository extends JpaRepository<RoutineProgress, Long> {
    // 특정 사용자의 특정 기간 동안의 루틴 진행 기록 조회
    //List<RoutineProgress> findByRoutineUserAndRoutineProgressDateBetween(User user, LocalDate startDate, LocalDate endDate);
    //List<RoutineProgress> findByRoutineInAndRoutineProgressDateBetween(
     //       List<Routine> routines, LocalDate start, LocalDate end);

    Optional<RoutineProgress> findByRoutineAndRoutineProgressCompletionTime(Routine routine, LocalDateTime date);
}
