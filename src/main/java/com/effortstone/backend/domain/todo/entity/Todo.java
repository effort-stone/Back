package com.effortstone.backend.domain.todo.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "todo")
@Table(name = "todo")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Setter
public class Todo extends BaseEntity {

    @Id
    @SequenceGenerator(name = "todo_seq", sequenceName = "todo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
    @Column(name = "todo_code")
    private Long todoCode;

    @Column(name = "todo_name")
    private String todoName;

    @Column(name = "todo_alert")
    private LocalTime todoAlert;

    @Column(name = "todo_detail")
    private String todoDetail;

    // 실행해야하는 날짜.
    @Column(name = "todo_date")
    private LocalDate todoDate;

    // 실행완료 날짜.
    @Column(name = "todo_completed_date")
    private LocalDate todoCompletedDate;

    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    @ToString.Exclude //StackOverflowError방지
    @JsonBackReference
    private User user;

}
