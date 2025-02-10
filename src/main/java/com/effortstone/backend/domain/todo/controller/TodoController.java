package com.effortstone.backend.domain.todo.controller;


import com.effortstone.backend.domain.todo.dto.request.TodoRequestDto;
import com.effortstone.backend.domain.todo.entity.Todo;
import com.effortstone.backend.domain.todo.service.TodoService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    // 🔹 모든 TODO 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Todo>>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        ApiResponse<List<Todo>> response = ApiResponse.success(SuccessCode.TODO_LIST_FETCH_SUCCESS, todos);
        return ResponseEntity.ok(response);
    }

    // 🔹 특정 TODO 조회
    @GetMapping("/{todoCode}")
    public ResponseEntity<ApiResponse<Todo>> getTodo(@PathVariable Long todoCode) {
        Todo todo = todoService.getTodoById(todoCode);
        ApiResponse<Todo> response = ApiResponse.success(SuccessCode.TODO_FETCH_SUCCESS, todo);
        return ResponseEntity.ok(response);
    }

    // 🔹 TODO 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Todo>> createTodo(@RequestBody TodoRequestDto.TodoCreateRequest todo) {
        Todo createdTodo = todoService.createTodo(todo);
        ApiResponse<Todo> response = ApiResponse.success(SuccessCode.TODO_CREATE_SUCCESS, createdTodo);
        return ResponseEntity.ok(response);
    }

    // 🔹 TODO 수정
    @PutMapping("/{todoCode}")
    public ResponseEntity<ApiResponse<Todo>> updateTodo(
            @PathVariable Long todoCode,
            @RequestBody Todo todoDetails) {
        Todo updatedTodo = todoService.updateTodo(todoCode, todoDetails);
        ApiResponse<Todo> response = ApiResponse.success(SuccessCode.TODO_UPDATE_SUCCESS, updatedTodo);
        return ResponseEntity.ok(response);
    }

    // 🔹 TODO 삭제
    @DeleteMapping("/{todoCode}")
    public ResponseEntity<ApiResponse<Void>> deleteTodo(@PathVariable Long todoCode) {
        todoService.deleteTodo(todoCode);
        ApiResponse<Void> response = ApiResponse.success(SuccessCode.TODO_DELETE_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
