package com.effortstone.backend.domain.user.dto.request;


import com.effortstone.backend.domain.user.entity.Provider;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class UserRequestDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Setter
    public static class UserCreateRequest {
        private String uid;                 // SQLite: uid - User: userCode
        private String name;                // SQLite: name - User: userName
        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDateTime latestLogin;  // SQLite: latestLogin - User: UserLatestLogin
        private Integer level;              // SQLite: level - User: userStoneLevel
        private Integer exp;                // SQLite: exp - User: userStoneExp
        private Long sideObj;               // SQLite: sideObj - User: userSideObj
        private Long topObj;                // SQLite: topObj - User: userTopObj
        private Integer accountLinkType;     // SQLite: accountLinkType - User: userLoginProvider
        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDateTime linkDate;     // SQLite: linkDate - User: userLinkDate
        private Integer gender;              // SQLite: gender - User: userGender
        private String birthDay;            // SQLite: birthDay - User: userBirth
        private String number;              // SQLite: number - User: userPhone
        private Boolean alram;              // SQLite: alram - User: userIsAlert
        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDateTime subscriptionEndDate; // SQLite: subscriptionEndDate - User: userSubEnddate
        private Boolean isFreeTrialUsed;    // SQLite: isFreeTrialUsed - User: userFreeSub
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class UserUpdateRequest {
        private String uid;                 // SQLite: uid - User: userCode
        private String name;                // SQLite: name - User: userName
        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDateTime latestLogin;  // SQLite: latestLogin - User: UserLatestLogin
        private Integer level;              // SQLite: level - User: userStoneLevel
        private Integer exp;                // SQLite: exp - User: userStoneExp
        private Long sideObj;               // SQLite: 없음 - User: userSideObj
        private Long topObj;                // SQLite: 없음 - User: userTopObj
        private Integer player;
        private Integer bgObj;
        private Integer freeCoin;
        private Integer accountLinkType;     // SQLite: accountLinkType - User: userLoginProvider
        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDateTime linkDate;     // SQLite: linkDate - User: userLinkDate
        private Integer gender;              // SQLite: gender - User: userGender
        private String birthDay;            // SQLite: birthDay - User: userBirth
        private String number;              // SQLite: number - User: userPhone
        private Boolean alram;              // SQLite: alram - User: userIsAlert
        @Schema(example = "2025-05-05 15:33:22.777", type = "string")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        private LocalDateTime subscriptionEndDate; // SQLite: subscriptionEndDate - User: userSubEnddate
        private Integer dailyCount; //// SQLite: dailyCount - User: userDailyCount
        private Boolean isFreeTrialUsed;    // SQLite: isFreeTrialUsed - User: userFreeSub
    }
}
