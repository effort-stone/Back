package com.effortstone.backend.domain.stonewearableitme.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoneWearableItemDto {
    private Long itemCode;
    private String itemName;
    private Boolean stoneWearableItemEquipped;
}
