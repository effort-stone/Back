package com.effortstone.backend.domain.routine.entity;

import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "routine_progress" )
@Table(name = "routine_progress",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"routine_code", "routine_progress_completion_time"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RoutineProgress extends BaseEntity {

    @Id
    @SequenceGenerator(name = "routineProgressCode_seq", sequenceName = "routineProgressCode_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routineProgressCode_seq")
    private Long routineProgressCode;

    // 해당 진행 내역이 속한 루틴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_code", nullable = false)
    private Routine routine;

    // 해당 날짜에 루틴 완료 여부
    @Column(name = "routine_progress_completed", nullable = false)
    private Boolean routineProgressCompleted;

    // 기록시간 ( 체크형이랑 같이도 됌 )
    @Column(name = "routine_progress_completion_time")
    private LocalDateTime routineProgressCompletionTime;

    // 시간 기록형 루틴일 경우 기록된 시간 (분 단위, 예: 45 -> 45분)
    @Column(name = "routine_progress_recorded_amount")
    private Integer routineProgressRecordedAmount;
}