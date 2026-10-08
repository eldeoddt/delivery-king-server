package com.king.deliveryking.order.dto.response;

import com.king.deliveryking.order.OrderStatus;
import com.king.deliveryking.order.entity.Order;

public record OrderResponseDTO(

        Long orderId,
        Long menuId,
        String menuName,
        OrderStatus status
) {

    public static OrderResponseDTO from(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getMenu().getId(),
                order.getMenu().getName(),
                order.getStatus()
        );
    }
}
