package com.king.deliveryking.order.controller;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.security.LoginUserId;
import com.king.deliveryking.global.swagger.ApiErrorCodes;
import com.king.deliveryking.order.dto.request.OrderRequestDTO;
import com.king.deliveryking.order.dto.request.OrderStatusRequestDTO;
import com.king.deliveryking.order.dto.response.OrderResponseDTO;
import com.king.deliveryking.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.king.deliveryking.global.exception.ErrorCode.*;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "CUSTOMER만 가능. 결제 전 PENDING 상태로 주문을 생성하며, 총액은 메뉴 가격 × 수량으로 서버가 계산한다.")
    @ApiErrorCodes({INVALID_INPUT, UNAUTHORIZED, FORBIDDEN, MENU_NOT_FOUND})
    @Secured(UserRole.Authority.CUSTOMER)
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestBody @Valid OrderRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrder(userId, request));
    }

    // 리스트 조회
    @Operation(summary = "주문 목록 조회", description = "CUSTOMER는 본인 주문, OWNER는 본인 메뉴에 들어온 주문을 조회한다.")
    @ApiErrorCodes({UNAUTHORIZED, USER_NOT_FOUND})
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrderList(
            @Parameter(hidden = true) @LoginUserId Long userId
    ) {
        return ResponseEntity.ok(orderService.getOrderList(userId));
    }

    // 주문 취소
    @Operation(summary = "주문 취소", description = "CUSTOMER만 가능. 본인 주문이 PENDING(주문요청) 상태일 때만 취소할 수 있다.")
    @ApiErrorCodes({UNAUTHORIZED, FORBIDDEN, ORDER_NOT_FOUND, CANNOT_CANCEL_ORDER})
    @Secured(UserRole.Authority.CUSTOMER)
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @Parameter(description = "주문 id", example = "1") @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(userId, orderId));
    }


    // 주문 상태 변경
    // 결제완료 주문수락
    // 주문수락 배달완료 200
    // 그외변경 모두불가.
    @Operation(summary = "주문 상태 변경", description = "OWNER만 가능. 본인 메뉴의 주문만 WAITING → ACCEPTED, ACCEPTED → COMPLETED로 변경할 수 있다.")
    @ApiErrorCodes({INVALID_INPUT, UNAUTHORIZED, FORBIDDEN, ORDER_NOT_FOUND, INVALID_ORDER_STATUS})
    @Secured(UserRole.Authority.OWNER)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @Parameter(description = "주문 id", example = "1") @PathVariable Long orderId,
            @RequestBody @Valid OrderStatusRequestDTO request
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(userId, orderId, request.status()));
    }
}
