package com.king.deliveryking.payment.service;

import com.king.deliveryking.payment.dto.response.PaymentResponseDTO;
import com.king.deliveryking.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 결제
    // 본인 주문만 결제 가능
    // 카드만 사용, 결제 금액은 서버에서 받음. 결제됐거나 취소된 주문은 x
    // 성공 시 저장, 주문상태를 완료로 바꿈.

}
