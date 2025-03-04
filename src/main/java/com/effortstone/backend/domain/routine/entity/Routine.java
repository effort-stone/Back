package com.effortstone.backend.domain.routine.entity;

import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "routines")
@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "routines")
@Setter
public class Routine extends BaseEntity {

    @Id
    @Column(name = "routine_code")
    @SequenceGenerator(name = "routine_seq", sequenceName = "routine_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routine_seq")
    private Long routineCode;

    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    @ToString.Exclude //StackOverflowError방지
    @JsonBackReference
    private User user;

    // 루틴이름
    @Column(name = "routine_name")
    private String routineName;

    // 루틴 목표
    @Enumerated(EnumType.STRING)
    @Column(name = "routine_goal_type", nullable = false) //
    private RoutineGoalType routineGoalType;

    // 시간 기록형일 경우만 입력 (null 가능) - front와 합의해 integer 값으로 변경
    @Column(name = "routine_focus_time")
    private Integer routineFocusTime;

    // 반복주기 ( 월,화,수,목,금,매일, JSON형식으로 처리함)
    @ElementCollection
    @CollectionTable(
            name = "routine_repeat_days",             // 생성될 테이블명
            joinColumns = @JoinColumn(name = "routine_id") // FK 컬럼
    )
    @Column(name = "day_of_week") // 실제 요일 값을 저장할 컬럼명
    private List<Integer> routineRepeatFrequency;

    // 루틴 시작일
    @Column(name = "routine_startdate")
    private LocalDate routineStartDate;

    //루틴 종료일(없으면 계속), null 가능 routineEndDate == null이면 "기한 없음"으로 해석
    @Column(name = "routine_enddate")
    private LocalDate routineEndDate;

    // 루틴 테마(건강... 등등)
    @Column(name = "routine_theme")
    @Enumerated(EnumType.ORDINAL) // Enum 이름을 숫자로 저장
    private RoutineTheme routineTheme;

    // 루틴 세부정보(메모)
    @Column(name = "routine_detail")
    private String routineDetail;

    // 루틴 시작시간 몇시부터 시작할껀지에 대함.
    @Column(name = "routine_starttime")
    private LocalTime routineStartTime;

    // 루틴 종료시간 몇시까지 종료할껀지에 대함
    @Column(name = "routine_endtime")
    private LocalTime routineEndTime;

    //루틴 알람시간
    @Column(name = "routine_alert_time")
    private LocalTime routineAlertTime;
}
