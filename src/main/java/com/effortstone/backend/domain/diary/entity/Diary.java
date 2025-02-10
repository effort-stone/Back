package com.effortstone.backend.domain.diary.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.user.entity.RoleType;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity(name="diary")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
public class Diary extends BaseEntity {

    @Id
    @SequenceGenerator(name = "diary_seq", sequenceName = "diary_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diary_seq")
    @Column(name = "diary_code")
    private Long diaryCode;

    @Column(name = "diary_title")
    private String diaryTitle;

    @Column(name = "diary_content")
    private String diaryContent;

    @Column(name = "diary_Date")
    @CreatedDate
    private LocalDate diaryDate;

    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    @ToString.Exclude //StackOverflowError방지
    @JsonBackReference
    private User user;
}
