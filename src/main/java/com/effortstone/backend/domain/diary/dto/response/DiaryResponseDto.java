package com.effortstone.backend.domain.diary.dto.response;

import com.effortstone.backend.domain.diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
public class DiaryResponseDto {
    private Long id; //Diary Code
    private String content; // Diary content
    private LocalDate dateTime;    // 일기가 작성된 날짜
    // 날짜별로 실행되어야 하는 루틴 목록을 전달 (예: "2025-04-29" → [루틴DTO, ...])
    //private Map<LocalDate, List<DiaryDto>> dailyDiaries;

    public static DiaryResponseDto fromEntity(Diary diary) {
        return DiaryResponseDto.builder()
                .id(diary.getDiaryCode())
                .content(diary.getDiaryContent())
                .dateTime(LocalDate.from(diary.getCreatedAt()))
                .build();
    };
}
