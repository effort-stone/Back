package com.effortstone.backend.domain.common;

import com.effortstone.backend.domain.diary.dto.response.DiaryResponseDto;
import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.routine.dto.response.RoutineProgressResponseDto;
import com.effortstone.backend.domain.routine.dto.response.RoutineResponseDto;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.todo.dto.response.TodoResponseDto;
import com.effortstone.backend.domain.todo.entity.Todo;
import com.effortstone.backend.domain.user.dto.response.UserResponseDto;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.useritem.dto.response.UserItemResponseDto;
import com.effortstone.backend.domain.useritem.entity.UserItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    UserResponseDto toUserResponseDto(User user);
    DiaryResponseDto toDiaryResponseDto(Diary diary);
    RoutineResponseDto toRoutineResponseDto(Routine routine);
    TodoResponseDto toTodoResponseDto(Todo todo);
    RoutineProgressResponseDto toRoutineProgressResponseDto(RoutineProgress routineProgress);
    UserItemResponseDto toUserItemResponseDto(UserItem userItem);
}
