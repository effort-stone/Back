package com.effortstone.backend.domain.stonewearableitme.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.stone.entity.Stone;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "stone_wearable_item")
@Table(name = "stone_wearable_item")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class StoneWearableItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stone_wearable_item_code",nullable = false)
    private Long stoneWearableItemCode;

    @ManyToOne
    @JoinColumn(name = "stone_id", nullable = false)
    private Stone stone;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // 착용 여부
    @Column(name = "stone_wearable_item_equipped")
    private Boolean stoneWearableItemEquipped;


    // setter를 외부에 노출하지 않고 내부 업데이트 메서드를 제공
    public void updateEquippedStatus(boolean equipped) {
        this.stoneWearableItemEquipped = equipped;
    }

    // Getter 추가 (필드가 boolean이면 자동으로 isXXX 형태가 됨)
    public boolean isStoneWearableItemEquipped() {
        return stoneWearableItemEquipped;
    }

}
