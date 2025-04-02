package com.effortstone.backend.domain.subscriptionpurchase.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "subscriptionpurchases")
@Table(name = "subscriptionpurchases")
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
    @Column(name = "auto_renewing")
    private Boolean autoRenewing;
    // 주문 아이디
    @Column(name = "order_id")
    private String orderId;
    // 구독 시작일을 LocalDateTime으로 저장
    @Column(name = "start_time")
    private LocalDateTime startTime;
    // 구독 만료일을 LocalDateTime으로 저장
    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
    //결제한 스토어
    @Column(name = "source")
    private String source;


}
