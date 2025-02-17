package com.effortstone.backend.domain.subscription.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "subscription")
@Table(name = "subscription")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class Subscription extends BaseEntity {

    @Id
    @Column(name = "subscription_code", nullable = false)
    private String subscriptionCode;

    //상품명
    @Column(name = "subscription_name")
    private String subscriptionName;

    //상품가격
    @Column(name = "subscription_price")
    private Integer subscriptionPrice;

    //상품구독기간
    @Column(name = "subscription_duration")
    private Integer subscriptionDuration;

    //상품상세설명
    @Column(name = "subscription_description")
    private String subscriptionDescription;

    //상품타입
    @Column(name = "subscription_type")
    private String subscriptionType;


}
