package com.effortstone.backend.domain.routine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "routine_progress" )
@Table(name = "routine_progress",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"routine_code", "routine_progress_date"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RoutineProgress {

    @Id
    @SequenceGenerator(name = "routineProgressCode_seq", sequenceName = "routineProgressCode_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routineProgressCode_seq")
    private Long routineProgressCode;

    // 해당 진행 내역이 속한 루틴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_code", nullable = false)
    private Routine routine;

    // 진행 내역이 기록된 날짜 (예: 2025-02-05)
    @Column(name = "routine_progress_date", nullable = false)
    private LocalDate routineProgressDate;

    // 해당 날짜에 루틴 완료 여부
    @Column(name = "routine_progress_completed", nullable = false)
    private Boolean routineProgressCompleted;

    // 체크형 루틴일 경우 완료 시각 (없으면 null)
    @Column(name = "routine_progress_completion_time")
    private LocalDateTime routineProgressCompletionTime;

    // 시간 기록형 루틴일 경우 기록된 시간 (분 단위, 예: 45 -> 45분)
    @Column(name = "routine_progress_recorded_amount")
    private Integer routineProgressRecordedAmount;
}