package com.effortstone.backend.domain.notices.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticesRequestDto {
    private String title;
    private String content;
}
