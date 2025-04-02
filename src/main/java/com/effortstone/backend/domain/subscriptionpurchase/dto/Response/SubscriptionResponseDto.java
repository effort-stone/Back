package com.effortstone.backend.domain.subscriptionpurchase.dto.Response;

import com.effortstone.backend.domain.subscriptionpurchase.entity.SubscriptionPurchases;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubscriptionResponseDto {
    private Boolean autoRenewing;
    private String orderId;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startTime;
    @Schema(example = "2025-05-05 15:33:22.777", type = "string")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime expiryTime;
    private String source;


    public static SubscriptionResponseDto fromEntity(SubscriptionPurchases subscriptionPurchases) {
        return SubscriptionResponseDto.builder()
                .autoRenewing(subscriptionPurchases.getAutoRenewing())
                .orderId(subscriptionPurchases.getOrderId())
                .startTime(subscriptionPurchases.getStartTime())
                .expiryTime(subscriptionPurchases.getExpiryTime())
                .source(subscriptionPurchases.getSource())
                .build();
    }
}
