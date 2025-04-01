package com.effortstone.backend.domain.notices.dto.response;

import com.effortstone.backend.domain.diary.dto.response.DiaryResponseDto;
import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.notices.entity.Notices;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class NoticesResponseDto {
    private String title;
    private String content;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime datetime;

    public static NoticesResponseDto fromEntity(Notices notice) {
        return NoticesResponseDto.builder()
                .title(notice.getNoticeTitle())
                .content(notice.getNoticeContent())
                .datetime(notice.getCreatedAt())
                .build();
    };
}
