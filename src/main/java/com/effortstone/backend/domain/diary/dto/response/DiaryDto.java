package com.effortstone.backend.domain.diary.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class DiaryDto {
    private Long id;
    private String content;
    private LocalDateTime date;    // 일기가 작성된 날짜
}
