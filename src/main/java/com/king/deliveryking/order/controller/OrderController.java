package com.king.deliveryking.order.controller;

import com.king.deliveryking.global.security.LoginUserId;
import com.king.deliveryking.global.swagger.ApiErrorCodes;
import com.king.deliveryking.order.dto.request.OrderRequestDTO;
import com.king.deliveryking.order.dto.response.OrderResponseDTO;
import com.king.deliveryking.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.king.deliveryking.global.exception.ErrorCode.*;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "결제 전 PENDING 상태로 주문을 생성한다. 삭제된 메뉴는 주문할 수 없다.")
    @ApiErrorCodes({INVALID_INPUT, UNAUTHORIZED, MENU_NOT_FOUND})
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestBody @Valid OrderRequestDTO request) {

        return ResponseEntity.ok(orderService.createOrder(userId, request));
    }
}
