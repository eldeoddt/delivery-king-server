package com.king.deliveryking.order.controller;

import com.king.deliveryking.global.security.LoginUserId;
import com.king.deliveryking.order.dto.request.OrderRequestDTO;
import com.king.deliveryking.order.dto.response.OrderResponseDTO;
import com.king.deliveryking.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @LoginUserId Long userId,
            @RequestBody @Valid OrderRequestDTO request) {

        return ResponseEntity.ok(orderService.createOrder(userId, request));
    }
}
