package com.king.deliveryking.order;

// 주문/배달 흐름: PENDING → WAITING → ACCEPTED → COMPLETED
// 고객 취소는 PENDING(주문요청)일 때만 가능
public enum OrderStatus {
    PENDING,     // 주문 생성, 결제 전
    WAITING,     // 결제 완료, 사장님 수락 대기
    ACCEPTED,    // 사장님 수락 (조리 중)
    COMPLETED,   // 배달 완료
    CANCELED ;    // 취소

    // 상태 변경 토글
    public boolean canChangeTo(OrderStatus next) {
        return (this == WAITING && next == ACCEPTED)
                || (this == ACCEPTED && next == COMPLETED);
    }
}
