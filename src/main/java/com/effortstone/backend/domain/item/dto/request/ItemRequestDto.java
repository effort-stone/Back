package com.effortstone.backend.domain.item.dto.request;

import com.effortstone.backend.domain.item.entity.ItemType;
import com.effortstone.backend.domain.item.entity.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private String itemName;
    private ItemType itemType;
    private int itemPrice;
    private PriceType itemPriceType;
    private String itemUrl;
    private double itemRatio;
    private double itemSrcSizeX;
    private double itemSrcSizeY;
    private int itemRow;
    private double itemStepTime;
    private int itemFrom;
    private int itemTo;
    private double itemPositionX;
    private double itemPositionY;
}