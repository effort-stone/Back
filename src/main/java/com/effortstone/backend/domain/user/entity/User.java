package com.effortstone.backend.domain.user.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Date;


@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Setter
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

//    @Column(name = "user_email")
//    private String userEmail;

    @Column(name = "user_login_provider")
    @Enumerated(EnumType.ORDINAL) // Enum 이름을 숫자로 저장
    private Provider userLoginProvider; // enum 으로 수정 하기
    // 0: 익명, 1 : 구글, 2 : 애플, 3 : 넘버

//    // 유저 구독 여부
//    @Column(name = "user_is_sub")
//    @Builder.Default()
//    private Boolean userIsSub = false;

    // 구독 마감 날짜 추가
    @Column(name = "user_sub_enddate")
    private LocalDateTime userSubEnddate;

    // 링크데이트( 계정 연동 시기 ) 추가
    @Column(name = "user_link_date")
    private LocalDateTime userLinkDate;

    // 마지막 로그인
    @Column(name = "user_latest_login")
    private LocalDateTime userLatestLogin;

    // 무료 구독 썻는지 안썼느지 ( boolean )
    @Column(name = "user_free_sub", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean userFreeSub = false;

    // 캐릭터 정보 ( 캐릭터 정보와 캐릭터가 착용하고 있는 정보에 대한 것 추가 ) 2개
    @Column(name = "user_level")
    @Builder.Default
    private Integer userLevel = 1;

    @Column(name = "user_Exp")
    @ColumnDefault("0")
    private int userExp;
    // 캐릭터가 착용하고 있는 정보에 대한 것 추가

    //sideObj
    @Column(name = "user_side_obj")
    private Long userSideObj;

    //topObj
    @Column(name = "user_top_obj")
    private Long userTopObj;

    //userPlayer
    @Column(name = "user_player")
    private Integer userPlayer;

    //userBackGroundObj
    @Column(name = "user_background_obj")
    private Integer userBackGroundObj;

    //userFreeCoin
    @Column(name = "user_free_coin")
    private Integer userFreeCoin;

    //유저 푸시알람 여부
    @Column(name = "user_is_alert", nullable = false, columnDefinition = "boolean default false")
    // columnDefinition = "boolean default false": Hibernate가 DDL을 생성할 때 컬럼에 기본값을 설정하도록 지시합니다.
    @Builder.Default
    private Boolean userIsAlert = false;

    // 유저 일일 퀘스트 수
    @Column(name="user_daily_count")
    private Integer userDailyCount;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    @Column(name = "user_role_type")
    private RoleType roleType = RoleType.USER;

}
