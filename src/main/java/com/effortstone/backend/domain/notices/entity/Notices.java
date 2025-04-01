package com.effortstone.backend.domain.notices.entity;

import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Builder
public class Notices extends BaseEntity {
    @Id
    @SequenceGenerator(name = "notice_seq", sequenceName = "notice_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq")
    @Column(name = "notice_code")
    private Long noticeCode;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

}
