package com.king.deliveryking.order;

// 주문/배달 흐름: PENDING → WAITING → ACCEPTED → DELIVERING → COMPLETED
// 취소는 WAITING일 때만 가능 (고객 취소 / 사장님 거절)
public enum OrderStatus {
    PENDING,     // 주문 생성, 결제 전
    WAITING,     // 결제 완료, 사장님 수락 대기
    ACCEPTED,    // 사장님 수락 (조리 중)
    DELIVERING,  // 배달 중
    COMPLETED,   // 배달 완료
    CANCELED     // 취소
}
