package com.effortstone.backend.domain.routine.dto.requset;
import com.effortstone.backend.domain.routine.entity.RoutineProgress;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class RoutineProgressRequestDto {
    private Long goalId; // routineCode;
    private Boolean completed;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime recodeTime; //completionTime;
    private Integer currentEffortTime;  // routineProgressRecordedAmount
    private boolean status; // status
}
