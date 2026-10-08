package com.king.deliveryking.order.repository;

import com.king.deliveryking.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByMenuUserId(Long userId);
}
