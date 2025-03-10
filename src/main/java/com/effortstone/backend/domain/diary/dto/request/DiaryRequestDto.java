package com.effortstone.backend.domain.diary.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class DiaryRequestDto {
    private String content; // diaryContent;
}
