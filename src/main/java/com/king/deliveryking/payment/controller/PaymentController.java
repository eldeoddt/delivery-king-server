package com.king.deliveryking.payment.controller;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.security.LoginUserId;
import com.king.deliveryking.global.swagger.ApiErrorCodes;
import com.king.deliveryking.payment.dto.request.PaymentRequestDTO;
import com.king.deliveryking.payment.dto.response.PaymentResponseDTO;
import com.king.deliveryking.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.king.deliveryking.global.exception.ErrorCode.*;

@Tag(name = "Payment", description = "결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제", description = "CUSTOMER만 가능. 본인의 PENDING 주문을 주문 총액으로 결제하고, 주문 상태를 WAITING(결제완료)으로 바꾼다.")
    @ApiErrorCodes({INVALID_INPUT, UNAUTHORIZED, FORBIDDEN, ORDER_NOT_FOUND, CANNOT_PAY_ORDER})
    @Secured(UserRole.Authority.CUSTOMER)
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> pay(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestBody @Valid PaymentRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.pay(userId, request));
    }
}
