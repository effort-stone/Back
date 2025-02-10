package com.effortstone.backend.domain.diary.dto.request;

import lombok.*;

public class DiaryRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class DiaryCreateRequest {
        private String diaryTitle;
        private String diaryContent;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class DiaryUpdateRequest {
        private String diaryTitle;
        private String diaryContent;
    }
}
