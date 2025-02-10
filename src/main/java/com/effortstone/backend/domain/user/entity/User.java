package com.effortstone.backend.domain.user.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class User extends BaseEntity {

    @Id
    @Column(name = "user_code")
    private String userCode;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_birth")
    private String userBirth;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_gender")
    private String userGender;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_login_provider")
    private String userLoginProvider;

    // 유저 구독 여부
    @Column(name = "user_is_sub")
    @Builder.Default()
    private Boolean userIsSub = false;

    //유저 푸시알람 여부
    @Column(name = "user_is_alert")
    @Builder.Default()
    private Boolean userIsAlert = false;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    @Column(name = "user_role_type")
    private RoleType roleType = RoleType.USER;

}
