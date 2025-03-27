package com.effortstone.backend.domain.todo.dto.response;

import com.effortstone.backend.domain.routine.dto.response.RoutineProgressResponseDto;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.todo.entity.Todo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@Setter
public class TodoResponseDto {
    private Long id;              // todoCode
    private String title;         // todoName
    @Schema(example = "08:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime alram;      // todoAlert
    private LocalDate dateTime;   // todoDate
    private String memo;          // todoDetail
//    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDate completedDate;


    public static TodoResponseDto fromEntity(Todo todo) {
        return TodoResponseDto.builder()
                .id(todo.getTodoCode())
                .title(todo.getTodoName())
                .alram(todo.getTodoAlert())
                .dateTime(todo.getTodoDate())
                .memo(todo.getTodoDetail())
                .completedDate(todo.getTodoCompletedDate())
                .build();
    }

}
