package com.king.deliveryking.payment.repository;

import com.king.deliveryking.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}