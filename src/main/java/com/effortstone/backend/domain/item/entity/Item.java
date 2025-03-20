package com.effortstone.backend.domain.item.entity;


import com.effortstone.backend.domain.common.BaseEntity;
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
    private ItemType itemType;

    @Column(name = "item_price")
    private int itemPrice;

    @Column(name = "item_price_type")
    private PriceType itemPriceType;

    @Column(name = "item_url")
    private String itemUrl;

    // 예: itemRatio: 0.15
    @Column(name = "item_ratio")
    private double itemRatio;

    // 예: srcSize의 x 값: 200
    @Column(name = "item_src_size_x")
    private double itemSrcSizeX;

    // 예: srcSize의 y 값: 200
    @Column(name = "item_src_size_y")
    private double itemSrcSizeY;

    // 예: itemRow: 0
    @Column(name = "item_row")
    private int itemRow;

    // 예: itemStepTime: 0.2
    @Column(name = "item_step_time")
    private double itemStepTime;

    // 예: itemFrom: 0
    @Column(name = "item_from")
    private int itemFrom;

    // 예: itemTo: 4
    @Column(name = "item_to")
    private int itemTo;

    // 예: itemPositionX: 0.65
    @Column(name = "item_position_x")
    private double itemPositionX;

    // 예: itemPositionY: 0.65
    @Column(name = "item_position_y")
    private double itemPositionY;



    // 엔티티 업데이트를 위한 도메인 메서드
    public void update(String itemName, ItemType itemType, Integer itemPrice) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
    }
}
