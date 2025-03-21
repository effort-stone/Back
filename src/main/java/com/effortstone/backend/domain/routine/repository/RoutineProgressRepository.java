package com.effortstone.backend.domain.routine.repository;

import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineProgressRepository extends JpaRepository<RoutineProgress, Long> {

    //. N+1 문제 해결 (Fetch Join 사용)
    // RoutineProgress를 조회할 때 연관된 Routine 데이터를 한 번에 로딩하도록 fetch join을 사용하면,
    // 각 RoutineProgress마다 별도의 Routine 조회가 발생하는 N+1 문제 발생
    @Query("SELECT DISTINCT rp FROM routine_progress rp JOIN FETCH rp.routine r WHERE r.user.userCode = :userCode")
    List<RoutineProgress> findByUserCodeWithRoutine(@Param("userCode") String userCode);


    // 특정 사용자의 특정 기간 동안의 루틴 진행 기록 조회
    //List<RoutineProgress> findByRoutineUserAndRoutineProgressDateBetween(User user, LocalDate startDate, LocalDate endDate);
    //List<RoutineProgress> findByRoutineInAndRoutineProgressDateBetween(
     //       List<Routine> routines, LocalDate start, LocalDate end);

    Optional<RoutineProgress> findByRoutineAndRoutineProgressCompletionTime(Routine routine, LocalDateTime date);
}
