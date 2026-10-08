package com.king.deliveryking.payment;

// 결제 흐름: READY → PAID / FAILED, 주문 취소 시 PAID → CANCELED
public enum PaymentStatus {
    READY,     // 결제 대기
    PAID,      // 결제 완료
    FAILED,    // 결제 실패
    CANCELED   // 결제 취소(환불)
}
