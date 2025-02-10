package com.effortstone.backend.domain.routine.repository;

import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.user.entity.User;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findByUser_UserCode(String userCode);
    // 루틴 정보를 얻기 위함. (캘ㄹ린더)
    List<Routine> findByUser(User user);
}
