package com.effortstone.backend.domain.todo.service;


import com.effortstone.backend.domain.todo.dto.request.TodoRequestDto;
import com.effortstone.backend.domain.todo.dto.response.TodoResponseDto;
import com.effortstone.backend.domain.todo.entity.Todo;
import com.effortstone.backend.domain.todo.repository.TodoRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
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
    public ApiResponse<TodoResponseDto> createTodo(TodoRequestDto.TodoCreateRequest todo) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Todo newTodo = Todo.builder()
                .todoName(todo.getTitle())
                .todoAlert(todo.getAlram())
                .todoDetail(todo.getMemo())
                .todoDate(todo.getDateTime())
                .user(user)
                .build();
        todoRepository.save(newTodo);
        return ApiResponse.success(SuccessCode.TODO_CREATE_SUCCESS,mapToDTO(newTodo));
    }

    // 🔹 TODO 수정 (setter 적용)
    public ApiResponse<TodoResponseDto> updateTodo(Long todoCode, TodoRequestDto.TodoUpdateRequest todo) {
        String userCode = SecurityUtil.getCurrentUserCode();
        // 유저 검증
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Todo existingTodo = getTodoById(todoCode);
        // 기존 객체의 필드 업데이트
        existingTodo.setTodoName(todo.getTitle());
        existingTodo.setTodoAlert(todo.getAlram());
        existingTodo.setTodoDetail(todo.getMemo());
        existingTodo.setTodoDate(todo.getDateTime());
        existingTodo.setTodoCompletedDate(todo.getCompletedDate());

        todoRepository.save(existingTodo);
        return ApiResponse.success(SuccessCode.TODO_UPDATE_SUCCESS,mapToDTO(existingTodo));

    }

    // 🔹 TODO 삭제
    public void deleteTodo(Long todoCode) {
        Todo todo = getTodoById(todoCode);
        todoRepository.delete(todo);
    }

    // 🔹 TODO 월별 조회
    public Map<LocalDate, List<TodoResponseDto>> getMonthlyTodos(YearMonth yearMonth) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate monthStart = yearMonth.atDay(1);
        LocalDate monthEnd = yearMonth.atEndOfMonth();

        List<Todo> todos = todoRepository.findByUserAndTodoDateBetween(user, monthStart, monthEnd);
        Map<LocalDate, List<TodoResponseDto>> todoMap = new HashMap<>();

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
    public List<TodoResponseDto> findAllTodosForDate(LocalDate date) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Todo> todos = todoRepository.findByUserAndTodoDate(user, date);

        List<TodoResponseDto> todoResponseDtos = todos.stream()
                .map(this::mapToDTO) // 이미 만들어둔 mapToDTO 메서드를 사용
                .collect(Collectors.toList());
        return todoResponseDtos;
    }

    private TodoResponseDto mapToDTO(Todo todo) {
        return TodoResponseDto.builder()
                .id(todo.getTodoCode())              // todoCode
                .title(todo.getTodoName())           // todoName
                .alram(todo.getTodoAlert())          // todoAlert
                .dateTime(todo.getTodoDate())        // todoDate
                .memo(todo.getTodoDetail())          // todoDetail
                .completedDate(todo.getTodoCompletedDate()) // todoCompletedDate (이름 수정 반영)
                .build();
    }




}
