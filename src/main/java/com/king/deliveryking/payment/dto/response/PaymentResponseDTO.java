package com.king.deliveryking.payment.dto.response;

import com.king.deliveryking.order.OrderStatus;
import com.king.deliveryking.payment.PaymentMethod;
import com.king.deliveryking.payment.PaymentStatus;
import com.king.deliveryking.payment.entity.Payment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PaymentResponseDTO(

        @Schema(description = "결제 id", example = "1")
        Long paymentId,
        @Schema(description = "주문 id", example = "1")
        Long orderId,
        @Schema(description = "결제 금액 (주문 총액)", example = "14000")
        Integer price,
        @Schema(description = "결제 수단", example = "CREDIT_CARD")
        PaymentMethod method,
        @Schema(description = "결제 상태", example = "PAID")
        PaymentStatus status,
        @Schema(description = "결제 후 주문 상태", example = "WAITING")
        OrderStatus orderStatus,
        @Schema(description = "결제 시각", example = "2026-10-08T16:30:00")
        LocalDateTime paidAt
) {

    public static PaymentResponseDTO from(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPrice(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getOrder().getStatus(),
                payment.getCreatedAt()
        );
    }
}
