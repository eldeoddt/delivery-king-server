package com.king.deliveryking.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),

    // 도메인
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, "인증 정보가 올바르지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "중복된 사용자가 존재합니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 Email 입니다."),
    INVALID_OWNER_TOKEN(HttpStatus.FORBIDDEN, "사장님 암호가 틀려 등록이 불가능합니다."),
    ACCOUNT_LOCKED(HttpStatus.LOCKED, "계정이 잠겼습니다. 관리자에게 문의하세요."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
    CANNOT_CANCEL_ORDER(HttpStatus.CONFLICT, "이미 처리 중인 주문은 취소할 수 없습니다."),
    INVALID_ORDER_STATUS(HttpStatus.CONFLICT, "변경할 수 없는 주문 상태입니다.");

    private final HttpStatus status;
    private final String message;
}
