package com.king.deliveryking.order.dto.request;

import com.king.deliveryking.order.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderStatusRequestDTO(

        @NotNull(message = "변경할 상태는 필수입니다.")
        @Schema(description = "변경할 주문 상태 (WAITING → ACCEPTED, ACCEPTED → COMPLETED만 가능)", example = "ACCEPTED")
        OrderStatus status
) {
}
