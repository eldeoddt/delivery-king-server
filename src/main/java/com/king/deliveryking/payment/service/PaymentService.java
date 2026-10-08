package com.king.deliveryking.payment.service;

import com.king.deliveryking.global.exception.BusinessException;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.order.OrderStatus;
import com.king.deliveryking.order.entity.Order;
import com.king.deliveryking.order.repository.OrderRepository;
import com.king.deliveryking.payment.PaymentStatus;
import com.king.deliveryking.payment.dto.request.PaymentRequestDTO;
import com.king.deliveryking.payment.dto.response.PaymentResponseDTO;
import com.king.deliveryking.payment.entity.Payment;
import com.king.deliveryking.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    // 결제
    // 본인 주문만 결제 가능
    // 카드만 사용, 결제 금액은 서버에서 받음. 결제됐거나 취소된 주문은 x
    // 성공 시 저장, 주문상태를 완료로 바꿈.
    public PaymentResponseDTO pay(Long userId, PaymentRequestDTO request) {

        // order 가져오기
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND)); // 404

        // 다른 고객 주문 -> 403
        // order의 userid 가 요청받은 userid와 다른 경우
        if (!order.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        // 결제 전(주문요청) 주문만 결제 가능 -> 409
        // pending이 아니면 에러
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(ErrorCode.CANNOT_PAY_ORDER);
        }

        // payment 객체 생성
        Payment payment = Payment.builder()
                .order(order)
                .method(request.method())
                .build(); // READY 상태

        // 성공 응답 받은 경우

        // 결제 성공 상태 변경
        payment.changeStatus(PaymentStatus.PAID);

        // 최종 db에 저장
        paymentRepository.save(payment);

        // 결제 완료
        order.changeStatus(OrderStatus.WAITING);

        return PaymentResponseDTO.from(payment);
    }
}
