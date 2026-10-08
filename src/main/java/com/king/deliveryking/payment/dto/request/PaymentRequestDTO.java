package com.king.deliveryking.payment.dto.request;

import com.king.deliveryking.payment.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

// 결제 금액은 받지 않는다. 서버가 주문 총액으로 결제한다.
public record PaymentRequestDTO(

        @NotNull(message = "주문은 필수입니다.")
        @Schema(description = "결제할 주문 id", example = "1")
        Long orderId,

        @NotNull(message = "결제 수단은 필수입니다.")
        @Schema(description = "결제 수단 (현재 CREDIT_CARD만 지원)", example = "CREDIT_CARD")
        PaymentMethod method
) {
}
