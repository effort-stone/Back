package com.effortstone.backend.domain.useritem.dto.response;

import com.effortstone.backend.domain.user.dto.response.UserResponseDto;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.useritem.entity.UserItem;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserItemResponseDto {
    private Long userItemCode;
    private LocalDateTime createdAt;

    public static UserItemResponseDto fromEntity(UserItem userItem) {
        return UserItemResponseDto.builder()
                .userItemCode(userItem.getUserItemCode())
                .createdAt(userItem.getCreatedAt())
                .build();
    }
}
