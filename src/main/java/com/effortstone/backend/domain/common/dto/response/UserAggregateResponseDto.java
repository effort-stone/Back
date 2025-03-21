package com.effortstone.backend.domain.common.dto.response;

import com.effortstone.backend.domain.diary.dto.response.DiaryResponseDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressResponseDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineResponseDto;
import com.effortstone.backend.domain.todo.dto.response.TodoResponseDto;
import com.effortstone.backend.domain.user.dto.response.UserResponseDto;
import com.effortstone.backend.domain.useritem.dto.response.UserItemResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class UserAggregateResponseDto {
    private UserResponseDto user;
    private List<DiaryResponseDto> diaries;
    private List<RoutineResponseDto> routines;
    private List<TodoResponseDto> todos;
    private List<RoutineProgressResponseDto> routineProgresses;
    private List<UserItemResponseDto> userItems;

}
