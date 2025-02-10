package com.effortstone.backend.domain.todo.repository;

import com.effortstone.backend.domain.todo.entity.Todo;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserAndTodoDateBetween
            (User user, LocalDate startDate, LocalDate endDate);

    // 특정 사용자와 특정 날짜에 해당하는 Todo 목록 조회
    List<Todo> findByUserAndTodoDate(User user, LocalDate targetDate);

}
