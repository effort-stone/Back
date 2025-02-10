package com.effortstone.backend.domain.todo.service;


import com.effortstone.backend.domain.todo.dto.request.TodoRequestDto;
import com.effortstone.backend.domain.todo.dto.response.TodoDto;
import com.effortstone.backend.domain.todo.entity.Todo;
import com.effortstone.backend.domain.todo.repository.TodoRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // 🔹 모든 TODO 조회
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // 🔹 특정 TODO 조회
    public Todo getTodoById(Long todoCode) {
        return todoRepository.findById(todoCode)
                .orElseThrow(() -> new RuntimeException("TODO not found"));
    }

    // 🔹 TODO 생성 (Builder 적용)
    public Todo createTodo(TodoRequestDto.TodoCreateRequest todo) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Todo newTodo = Todo.builder()
                .todoName(todo.getTodoName())
                .todoAlert(todo.getTodoAlert())
                .todoDetail(todo.getTodoDetail())
                .todoDate(todo.getTodoDate())
                .user(user) // 🔹 유저 정보 추가
                .build();
        return todoRepository.save(newTodo);
    }

    // 🔹 TODO 수정 (Builder 적용)
    public Todo updateTodo(Long todoCode, Todo todoDetails) {
        Todo existingTodo = getTodoById(todoCode);

        Todo updatedTodo = Todo.builder()
                .todoCode(existingTodo.getTodoCode())  // 기존 ID 유지
                .todoName(todoDetails.getTodoName())
                .todoAlert(todoDetails.getTodoAlert())
                .todoDetail(todoDetails.getTodoDetail())
                .todoDate(todoDetails.getTodoDate())
                .build();

        return todoRepository.save(updatedTodo);
    }

    // 🔹 TODO 삭제
    public void deleteTodo(Long todoCode) {
        Todo todo = getTodoById(todoCode);
        todoRepository.delete(todo);
    }

    // 🔹 TODO 월별 조회
    public Map<LocalDate, List<TodoDto>> getMonthlyTodos(YearMonth yearMonth) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate monthStart = yearMonth.atDay(1);
        LocalDate monthEnd = yearMonth.atEndOfMonth();

        List<Todo> todos = todoRepository.findByUserAndTodoDateBetween(user, monthStart, monthEnd);
        Map<LocalDate, List<TodoDto>> todoMap = new HashMap<>();

        for (LocalDate date = monthStart; !date.isAfter(monthEnd); date = date.plusDays(1)) {
            todoMap.put(date, new ArrayList<>());
        }

        for (Todo todo : todos) {
            LocalDate date = (todo.getTodoDate());
            todoMap.get(date).add(mapToDTO(todo));
        }
        return todoMap;
    }

    /**
     * 특정 날짜(date)에 해당 사용자가 해야 할 TODO 목록 조회
     */
    // 🔹 TODO 오늘의 사용자 투두 조회
    public List<TodoDto> findAllTodosForDate(LocalDate date) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Todo> todos = todoRepository.findByUserAndTodoDate(user, date);

        List<TodoDto> todoDtos = todos.stream()
                .map(this::mapToDTO) // 이미 만들어둔 mapToDTO 메서드를 사용
                .collect(Collectors.toList());
        return todoDtos;
    }

    private TodoDto mapToDTO(Todo todo) {
        return TodoDto.builder()
                .todoCode(todo.getTodoCode())
                .todoName(todo.getTodoName())
                .todoAlert(todo.getTodoAlert())
                .todoDate(todo.getTodoDate())
                .todoDetail(todo.getTodoDetail())
                .todoCompleted(todo.getTodoCompleted())
                .build();
    }




}
