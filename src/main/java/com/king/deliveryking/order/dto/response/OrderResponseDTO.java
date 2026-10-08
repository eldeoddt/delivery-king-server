package com.king.deliveryking.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.king.deliveryking.order.OrderStatus;
import com.king.deliveryking.order.entity.Order;

public record OrderResponseDTO(

        @Schema(description = "주문 id", example = "1")
        Long orderId,
        @Schema(description = "메뉴 id", example = "1")
        Long menuId,
        @Schema(description = "메뉴 이름", example = "짜장면")
        String menuName,
        @Schema(description = "주문 상태", example = "PENDING")
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
