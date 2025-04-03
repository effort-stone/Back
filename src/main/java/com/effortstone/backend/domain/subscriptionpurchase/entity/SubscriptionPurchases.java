package com.effortstone.backend.domain.subscriptionpurchase.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.user.entity.User;
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

    // 주문 아이디
    @Id
    @Column(name = "order_id")
    private String orderId;

    // 자동 결제 여부
    @Column(name = "auto_renewing")
    private Boolean autoRenewing;
    // 구독 시작일을 LocalDateTime으로 저장
    @Column(name = "start_time")
    private LocalDateTime startTime;
    // 구독 만료일을 LocalDateTime으로 저장
    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
    //결제한 스토어
    @Column(name = "source")
    private String source;

    @ManyToOne(fetch = FetchType.LAZY) // 성능 최적화를 위한 지연 로딩, 유저를 불러오는 코드를 작성하기 전까진 불러오지 않음
    @JoinColumn(name = "user_code",nullable = false)
    private User user;
}
