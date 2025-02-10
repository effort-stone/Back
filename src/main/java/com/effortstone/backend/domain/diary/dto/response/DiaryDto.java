package com.effortstone.backend.domain.diary.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DiaryDto {
    private Long id;
    private String content;
    private LocalDate date;    // 일기가 작성된 날짜
}
