package com.king.deliveryking.order.service;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.exception.BusinessException;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.menu.MenuStatus;
import com.king.deliveryking.menu.entity.Menu;
import com.king.deliveryking.menu.repository.MenuRepository;
import com.king.deliveryking.order.OrderStatus;
import com.king.deliveryking.order.dto.request.OrderRequestDTO;
import com.king.deliveryking.order.dto.response.OrderResponseDTO;
import com.king.deliveryking.order.entity.Order;
import com.king.deliveryking.order.repository.OrderRepository;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    // 주문 생성 (결제 전 PENDING 상태)
    @Transactional
    public OrderResponseDTO createOrder(Long userId, OrderRequestDTO requestDTO) {
        // 메뉴 확인
        Menu menu = menuRepository.findByIdAndStatus(requestDTO.menuId(), MenuStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        // JWT 필터에서 이미 사용자를 조회했으므로 FK 연결용 프록시만 가져온다.
        User user = userRepository.getReferenceById(userId);

        Order order = Order.builder()
                .user(user)
                .menu(menu)
                .quantity(requestDTO.quantity())
                .address(requestDTO.address())
                .build();
        orderRepository.save(order);

        return OrderResponseDTO.from(order);
    }

    // 주문 리스트 조회
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrderList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 고객 -> 본인 주문
        if (user.getRole() == UserRole.CUSTOMER) {
            return orderRepository.findAllByUserId(userId).stream()
                    .map(OrderResponseDTO::from)
                    .toList();
        }

        // 사장 -> 본인 메뉴의 주문
        return orderRepository.findAllByMenuUserId(userId).stream()
                .map(OrderResponseDTO::from)
                .toList();
    }

    // 주문 취소
    public OrderResponseDTO cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND)); // 404

        // 다른 고객 주문 -> 403
        // 주문의 userid가 받은 userid와 다르면
        if (!order.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        // 결제 전(주문요청)일 때만 취소 가능 -> 409
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(ErrorCode.CANNOT_CANCEL_ORDER);
        }

        // 상태변경
        order.cancelOrder();

        return OrderResponseDTO.from(order);
    }

    // 대기-수락, 수락-배달완료 상태변경
    public OrderResponseDTO updateOrderStatus(Long userId, Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND)); // 404

        // 본인 메뉴에 들어온 주문만 -> 403
        if (!order.getMenu().getUser().getId().equals(userId)) { // order의 menu의 id가 받은 id와 동일하지 않으면
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        // 허용되지 않은 상태 변경 -> 409
        if (!order.getStatus().canChangeTo(status)) {
            throw new BusinessException(ErrorCode.INVALID_ORDER_STATUS);
        }

        order.changeStatus(status);

        return OrderResponseDTO.from(order);
    }
}
