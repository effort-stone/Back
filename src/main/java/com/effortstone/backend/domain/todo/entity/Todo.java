package com.effortstone.backend.domain.todo.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "todo")
@Table(name = "todo")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class Todo extends BaseEntity {

    @Id
    @SequenceGenerator(name = "todo_seq", sequenceName = "todo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq")
    @Column(name = "todo_code")
    private Long todoCode;

    @Column()
    private String todoName;

    @Column()
    private LocalTime todoAlert;

    @Column()
    private String todoDetail;

    @Column()
    @CreatedDate
    private LocalDate todoDate;

    @Column()
    @Builder.Default()
    private Boolean todoCompleted = false;

    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    @ToString.Exclude //StackOverflowError방지
    @JsonBackReference
    private User user;

}
