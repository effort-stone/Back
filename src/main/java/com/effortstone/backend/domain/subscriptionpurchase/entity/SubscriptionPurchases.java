package com.effortstone.backend.domain.subscriptionpurchase.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "payment")
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Setter
public class SubscriptionPurchases extends BaseEntity {

    @Id
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @Column(name = "subscription_purchase_code")
    private Long subscriptionPurchaseCode;

    // 자동 결제 여부
    private Boolean autoRenewing;
    // 주문 아이디
    private String orderId;
    // 구독 시작일을 LocalDateTime으로 저장
    private LocalDateTime startTime;
    // 구독 만료일을 LocalDateTime으로 저장
    private LocalDateTime expiryTime;


}
