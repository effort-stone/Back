package com.effortstone.backend.domain.subscription.controller;


import com.effortstone.backend.domain.subscription.dto.response.SubscriptionResponseDto;
import com.effortstone.backend.domain.subscription.entity.Subscription;
import com.effortstone.backend.domain.subscription.repository.SubscriptionRepository;
import com.effortstone.backend.domain.subscription.service.SubscriptionService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {


    private final SubscriptionService subscriptionService;

    /**
     * 구독 상품 단건 조회 API
     */
    @GetMapping("/{id}")
        public ApiResponse<SubscriptionResponseDto.SubscriptionGetRequest> getSubscription(@PathVariable String id) {
            SubscriptionResponseDto.SubscriptionGetRequest DTO = subscriptionService.getSubscription(id);
            return ApiResponse.success(SuccessCode.SUBSCRIPTION_GET_SUCCESS,DTO );
    };

    /**
     * 구독 상품 다건 조회
     */
    @GetMapping("/")
    public ApiResponse<List<SubscriptionResponseDto.SubscriptionGetRequest>> getSubscription() {
        List<SubscriptionResponseDto.SubscriptionGetRequest> Dto = subscriptionService.getAllSubscription();
        return ApiResponse.success(SuccessCode.SUBSCRIPTION_GET_SUCCESS,Dto);
    };
}
