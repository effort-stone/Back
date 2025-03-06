package com.effortstone.backend.domain.user.dto.response;

import com.effortstone.backend.domain.user.entity.Provider;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createDate;   // SQLite: createDate - User: 없음 (CreatedAt으로 대체 )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime latestLogin;  // SQLite: latestLogin - User: UserLatestLogin
    private Integer level;              // SQLite: level - User: userStoneLevel
    private Integer exp;                // SQLite: exp - User: userStoneExp
    private Long sideObj;                // SQLite: exp - User: userSideObj
    private Long topObj;              // SQLite: level - User: userTopObj
    private Provider accountLinkType;     // SQLite: accountLinkType - User: userLoginProvider
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime linkDate;     // SQLite: linkDate - User: userLinkDate
    private String gender;              // SQLite: gender - User: userGender
    private String birthDay;            // SQLite: birthDay - User: userBirth
    private String number;              // SQLite: number - User: userPhone
    private Boolean alram;              // SQLite: alram - User: userIsAlert
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime subscriptionEndDate; // SQLite: subscriptionEndDate - User: userSubEnddate
    private Boolean isFreeTrialUsed;    // SQLite: isFreeTrialUsed - User: userFreeSub
    private Boolean status;  // // SQLite: status - User: 없음 ( status 으로 대체 )


    public static UserResponseDto fromEntity(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUid(user.getUserCode());
        dto.setName(user.getUserName());
        dto.setCreateDate(user.getCreatedAt());  // BaseEntity에서 상속
        dto.setLatestLogin(user.getUserLatestLogin());
        dto.setLevel(user.getUserStoneLevel());
        dto.setExp(user.getUserStoneExp());
        dto.setSideObj(user.getUserSideObj());
        dto.setTopObj(user.getUserTopObj());
        dto.setAccountLinkType(user.getUserLoginProvider());
        dto.setLinkDate(user.getUserLinkDate());
        dto.setGender(user.getUserGender());
        dto.setBirthDay(user.getUserBirth());
        dto.setNumber(user.getUserPhone());
        dto.setAlram(user.getUserIsAlert());
        dto.setSubscriptionEndDate(user.getUserSubEnddate());
        dto.setIsFreeTrialUsed(user.getUserFreeSub());
        dto.setStatus(user.getStatus());
        return dto;
    }
}