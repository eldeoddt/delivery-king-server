package com.king.deliveryking.order.entity;

import com.king.deliveryking.global.entity.BaseEntity;
import com.king.deliveryking.order.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order") // sql 예약어이므로 p_order로 지정한다.
@Getter
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PAID;

    public Order(Long userId, Integer totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }
}