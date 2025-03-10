package com.effortstone.backend.domain.item.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.stonewearableitme.entity.StoneWearableItem;
import com.effortstone.backend.domain.user.entity.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "item")
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_code",nullable = false)
    private Long itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_price")
    private Integer itemPrice;

    @Column(name = "item_price_type")
    private Integer itemPriceType;

    @Column(name = "item_url")
    private String itemUrl;


    // 아이템 착용 캐릭터을 위함
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Builder.Default
    private List<StoneWearableItem> stoneWearableItems = new ArrayList<>();

    // 엔티티 업데이트를 위한 도메인 메서드
    public void update(String itemName, String itemType, Integer itemPrice) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
    }
}
