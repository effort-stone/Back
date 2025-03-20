package com.effortstone.backend.domain.useritem.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

//유저와 아이템의 조합에 대한 유니크 제약조건을 추가하여 동일한 조합이 중복 저장되지 않도록 합니다.

@Entity(name = "user_item")
@Table(name = "user_item",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "item_id"})})
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class UserItem extends BaseEntity {

    // 대리키로서의 id (Primary Key)
    @Id
    @SequenceGenerator(name = "user_item_seq", sequenceName = "user_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_item_seq")
    @Column(name = "user_item_code")
    private Long userItemCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stone_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

}
