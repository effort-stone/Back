package com.effortstone.backend.domain.payment.entity;


import com.effortstone.backend.domain.common.BaseEntity;
import com.effortstone.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "payment")
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class Payment extends BaseEntity {

    @Id
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @Column(name = "payment_code")
    private Long paymentCode;

    // 구독 상품
    //@ManyToOne
    //@JoinColumn(name = "subscription_code", nullable = false)
    //private Subscription subscription;

    // 유저 정보
    @ManyToOne
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    // 총 결제금액
    @Column(name = "payment_total_amount")
    private String paymentTotalAmount;

    //공급가액
    @Column(name = "payment_supplied_amount")
    private String paymentSuppliedAmount;

    // 부가세
    @Column(name = "payment_vat")
    private String paymentVat;

    //면세
    @Column(name = "payment_tax_free")
    private String paymentTaxFree;

    //결제상태
    @Column(name = "payment_status")
    private String paymentStatus;

    //결제수단
    @Column(name = "payment_method")
    private String paymentMethod;

    //외부결제
    @Column(name = "transaction_id")
    private String transactionId;

    //결제요청시간
    @Column(name = "payment_requested_at")
    private String paymentRequestedAt;

    //결제승인시간
    @Column(name = "payment_approved_at")
    private String paymentApprovedAt;


}
