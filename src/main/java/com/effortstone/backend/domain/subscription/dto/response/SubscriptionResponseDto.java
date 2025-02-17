package com.effortstone.backend.domain.subscription.dto.response;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class SubscriptionResponseDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Data
    public static class SubscriptionGetRequest {
        private String subscriptionCode;
        private String subscriptionName;
        private Integer subscriptionPrice;
        private Integer subscriptionDuration;
        private String subscriptionDescription;
        private String subscriptionType;
    }
}
