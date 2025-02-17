package com.effortstone.backend.domain.subscription.service;


import com.effortstone.backend.domain.item.dto.response.ItemResponseDto;
import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.subscription.dto.response.SubscriptionResponseDto;
import com.effortstone.backend.domain.subscription.entity.Subscription;
import com.effortstone.backend.domain.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;

    /**
     * 구독 상품 단건 조회
     */
    public SubscriptionResponseDto.SubscriptionGetRequest getSubscription(String subCode) {
        return convertToDto(Objects.requireNonNull(subscriptionRepository.findById(subCode).orElse(null)));
    }

    /**
     * 구독 상품 다건 조회
     */
    public List<SubscriptionResponseDto.SubscriptionGetRequest> getAllSubscription() {
        return subscriptionRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private SubscriptionResponseDto.SubscriptionGetRequest convertToDto(Subscription subscription) {
        return new SubscriptionResponseDto.SubscriptionGetRequest(
                subscription.getSubscriptionCode(),
                subscription.getSubscriptionName(),
                subscription.getSubscriptionPrice(),
                subscription.getSubscriptionDuration(),
                subscription.getSubscriptionDescription(),
                subscription.getSubscriptionType()
        );
    }
}
