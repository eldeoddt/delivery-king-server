package com.king.deliveryking.order.service;

import com.king.deliveryking.global.exception.BusinessException;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.menu.entity.Menu;
import com.king.deliveryking.menu.repository.MenuRepository;
import com.king.deliveryking.order.dto.request.OrderRequestDTO;
import com.king.deliveryking.order.dto.response.OrderResponseDTO;
import com.king.deliveryking.order.entity.Order;
import com.king.deliveryking.order.repository.OrderRepository;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    // 주문 생성 (결제 전 PENDING 상태)
    @Transactional
    public OrderResponseDTO createOrder(Long userId, OrderRequestDTO requestDTO) {
        // 메뉴 확인
        Menu menu = menuRepository.findById(requestDTO.menuId()).orElseThrow(
                () -> new BusinessException(ErrorCode.MENU_NOT_FOUND)
        );

        // JWT 필터에서 이미 사용자를 조회했으므로 FK 연결용 프록시만 가져온다.
        User user = userRepository.getReferenceById(userId);

        Order order = Order.builder()
                .user(user)
                .menu(menu)
                .build();
        orderRepository.save(order);

        return OrderResponseDTO.from(order);
    }
}
