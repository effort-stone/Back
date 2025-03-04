package com.effortstone.backend.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResponseDto {
    private String uid;                 // SQLite: uid - User: userCode
    private String name;                // SQLite: name - User: userName
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createDate;   // SQLite: createDate - User: 없음 (CreatedAt으로 대체 )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime latestLogin;  // SQLite: latestLogin - User: UserLatestLogin
    private Integer level;              // SQLite: level - User: userStoneLevel
    private Integer exp;                // SQLite: exp - User: userStoneExp
    private Long sideObj;                // SQLite: exp - User: userSideObj
    private Long topObj;              // SQLite: level - User: userTopObj
    private String accountLuserTopObjinkType;     // SQLite: accountLinkType - User: userLoginProvider
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime linkDate;     // SQLite: linkDate - User: userLinkDate
    private String gender;              // SQLite: gender - User: userGender
    private String birthDay;            // SQLite: birthDay - User: userBirth
    private String number;              // SQLite: number - User: userPhone
    private Boolean alram;              // SQLite: alram - User: userIsAlert
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime subscriptionEndDate; // SQLite: subscriptionEndDate - User: userSubEnddate
    private Boolean isFreeTrialUsed;    // SQLite: isFreeTrialUsed - User: userFreeSub
    private Boolean status;  // // SQLite: isFreeTrialUsed - User: 없음 ( status 으로 대체 )
}