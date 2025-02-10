package com.effortstone.backend.domain.stone.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.stonewearableitme.entity.StoneWearableItem;
import com.effortstone.backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "stone")
@Table(name = "stone")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
@ToString
@Getter
@DynamicInsert
public class Stone extends BaseEntity {

    @Id
    @SequenceGenerator(name = "stone_seq", sequenceName = "stone_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stone_seq")
    @Column(name = "stone_code")
    private Long stoneCode;

    @Column()
    private String stoneName;

    @Column()
    @ColumnDefault("1")
    private int stoneLevel;

    @Column()
    @ColumnDefault("0")
    private Long stoneExp = 0L;

    @OneToOne
    @JoinColumn(name = "user_code", nullable = false)
    @ToString.Exclude //StackOverflowError방지
    @JsonBackReference
    private User user;


    // 캐릭터 착용 아이템을 위함
    @OneToMany(mappedBy = "stone", cascade = CascadeType.ALL)
    private List<StoneWearableItem> stoneWearableItems = new ArrayList<>();
}
