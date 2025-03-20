package com.effortstone.backend.domain.useritem.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserItemResponseDto {
    private Long userItemCode;
}
