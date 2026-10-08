package com.king.deliveryking.order.entity;

import com.king.deliveryking.global.entity.BaseEntity;
import com.king.deliveryking.menu.entity.Menu;
import com.king.deliveryking.order.OrderStatus;
import com.king.deliveryking.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order") // sql 예약어이므로 p_order로 지정한다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING; // 주문 생성 시 결제 전 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // p_order 내 컬럼명을 지정한다.
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false) // p_order 내 컬럼명을 지정한다.
    private Menu menu;

    @Builder
    public Order(User user) {
        this.user = user;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }
}