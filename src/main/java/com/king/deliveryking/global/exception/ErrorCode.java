package com.king.deliveryking.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, "인증 정보가 올바르지 않습니다."),
    ACCOUNT_LOCKED(HttpStatus.LOCKED, "계정이 잠겼습니다. 관리자에게 문의하세요."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    CANNOT_CANCEL_SHIPPED(HttpStatus.CONFLICT, "배송이 시작된 주문은 취소할 수 없습니다."),
    CANNOT_CANCEL_DONE(HttpStatus.CONFLICT, "완료된 주문은 취소할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}