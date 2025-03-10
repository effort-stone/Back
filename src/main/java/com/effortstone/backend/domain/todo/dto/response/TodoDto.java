package com.effortstone.backend.domain.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Setter
public class TodoDto {
    private Long id;              // todoCode
    private String title;         // todoName
    @Schema(example = "08:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime alram;      // todoAlert
    private LocalDate dateTime;   // todoDate
    private String memo;          // todoDetail
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime completedDate;
    private Boolean isActive;

}
