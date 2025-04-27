package com.effortstone.backend.domain.routine.dto.requset;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class RoutineProgressUpdateRequestDto {
    private Long goalId; // routineCode;
    private Long recordId; // 루틴내역 코드 routineProgressCode
    private Boolean isAchieved;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime recordTime; //completionTime;
    private Integer currentEffortTime;  // routineProgressRecordedAmount
    // private boolean status; // status
}
