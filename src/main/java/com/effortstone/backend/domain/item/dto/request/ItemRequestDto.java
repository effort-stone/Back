package com.effortstone.backend.domain.item.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private String itemName;
    private String itemType;
    private Integer itemPrice;
}