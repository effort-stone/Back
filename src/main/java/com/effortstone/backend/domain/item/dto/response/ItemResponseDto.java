package com.effortstone.backend.domain.item.dto.response;


import com.effortstone.backend.domain.item.entity.ItemType;
import com.effortstone.backend.domain.item.entity.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    private Long itemCode;
    private String srcName;      // Entity의 itemUrl에 해당 (srcName으로 전달)
    private String name;         // Entity의 itemName에 해당
    private PriceType coinType;   // Entity의 itemPriceType을 변환하여 사용 (필요 시 변환 로직 적용)
    private int price;           // Entity의 itemPrice에 해당
    private ItemType objType;     // Entity의 itemType을 변환하여 사용 (필요 시 변환 로직 적용)
    private double ratio;        // Entity의 itemRatio에 해당
    private double srcSizeX;     // Entity의 item_src_size_x에 해당
    private double srcSizeY;     // Entity의 item_src_size_y에 해당
    private int row;             // Entity의 itemRow에 해당
    private double stepTime;     // Entity의 itemStepTime에 해당
    private int from;            // Entity의 itemFrom에 해당
    private int to;              // Entity의 itemTo에 해당
    private double positionX;    // Entity의 itemPositionX에 해당
    private double positionY;    // Entity의 itemPositionY에 해당
}