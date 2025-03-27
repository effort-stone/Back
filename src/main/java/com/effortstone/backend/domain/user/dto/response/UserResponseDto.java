package com.effortstone.backend.domain.user.dto.response;

import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
public class UserResponseDto {
    private String uid;                 // SQLite: uid - User: userCode
    private String name;                // SQLite: name - User: userName
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdDate;   // SQLite: createDate - User: 없음 (CreatedAt으로 대체 )
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime latestLogin;  // SQLite: latestLogin - User: UserLatestLogin
    private Integer level;              // SQLite: level - User: userStoneLevel
    private Integer exp;                // SQLite: exp - User: userStoneExp
    private Long sideObj;                // SQLite: sideObj - User: userSideObj
    private Long topObj;              // SQLite: topObj - User: userTopObj
    private Integer player;
    private Integer bgObj;
    private Integer freeCoin;
    private Integer accountLinkType;     // SQLite: accountLinkType - User: userLoginProvider
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime linkDate;     // SQLite: linkDate - User: userLinkDate
    private String gender;              // SQLite: gender - User: userGender
    private String birthDay;            // SQLite: birthDay - User: userBirth
    private String number;              // SQLite: number - User: userPhone
    private Boolean alram;              // SQLite: alram - User: userIsAlert
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime subscriptionEndDate; // SQLite: subscriptionEndDate - User: userSubEnddate
    private int dailyCount;  // SQLite: dailyCount - User: userDailyCount
    private Boolean isFreeTrialUsed;    // SQLite: isFreeTrialUsed - User: userFreeSub



    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .uid(user.getUserCode())
                .name(user.getUserName())
                .createdDate(user.getCreatedAt())
                .latestLogin(user.getUserLatestLogin())
                .level(user.getUserLevel())
                .exp(user.getUserExp())
                .sideObj(user.getUserSideObj())
                .topObj(user.getUserTopObj())
                .accountLinkType(user.getUserLoginProvider().getCode())
                .linkDate(user.getUserLinkDate())
                .gender(user.getUserGender())
                .birthDay(user.getUserBirth())
                .number(user.getUserPhone())
                .alram(user.getUserIsAlert())
                .subscriptionEndDate(user.getUserSubEnddate())
                .isFreeTrialUsed(user.getUserFreeSub())
                .player(user.getUserPlayer())
                .bgObj(user.getUserBackGroundObj())
                .freeCoin(user.getUserFreeCoin())
                .dailyCount(user.getUserDailyCount())
                .build();
    }
}