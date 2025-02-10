package com.effortstone.backend.domain.routine.dto.requset;
import com.effortstone.backend.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class RoutineProgressRequestDto {

    private Long routineCode;
    private LocalDate progressDate;
    private Boolean completed;
    private LocalDateTime completionTime;
    private Integer recordedAmount;
}
