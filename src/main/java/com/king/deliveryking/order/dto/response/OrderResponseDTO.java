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
        @Schema(description = "수량", example = "2")
        Integer quantity,
        @Schema(description = "총액 (메뉴 가격 × 수량)", example = "14000")
        Integer totalPrice,
        @Schema(description = "배송 주소", example = "서울시 강남구 테헤란로 123")
        String address,
        @Schema(description = "주문 상태", example = "PENDING")
        OrderStatus status
) {

    public static OrderResponseDTO from(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getMenu().getId(),
                order.getMenu().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getAddress(),
                order.getStatus()
        );
    }
}
