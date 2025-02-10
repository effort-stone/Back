package com.effortstone.backend.domain.stone.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StoneDto {
    private Long stoneCode;       // PK (조회나 업데이트 시 사용)
    private String stoneName;
    private Integer stoneLevel;
    private Long stoneExp;
    private String userCode;      // Stone이 연결될 사용자 식별자 (User.userCode)
}
