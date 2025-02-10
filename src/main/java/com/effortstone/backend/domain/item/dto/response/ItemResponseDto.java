package com.effortstone.backend.domain.item.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    private Long itemCode;
    private String itemName;
    private String itemType;
    private Integer itemPrice;
}