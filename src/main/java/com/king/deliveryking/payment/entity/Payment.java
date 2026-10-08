package com.king.deliveryking.payment.entity;

import com.king.deliveryking.global.entity.BaseEntity;
import com.king.deliveryking.order.entity.Order;
import com.king.deliveryking.payment.PaymentMethod;
import com.king.deliveryking.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer price; // 가격

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method = PaymentMethod.CREDIT_CARD; // 결제 수단 (기본값: 신용카드)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.READY; // 결제 생성 시 대기 상태

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public void changeStatus(PaymentStatus status) {
        this.status = status;
    }
}
