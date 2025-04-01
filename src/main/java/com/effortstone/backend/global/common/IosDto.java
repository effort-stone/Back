package com.effortstone.backend.global.common;

import lombok.Data;

@Data
public class IosDto {
    String receiptData;
    String password;
    Boolean excludeOldTransactions;
}
