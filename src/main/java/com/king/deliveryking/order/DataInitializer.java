package com.king.deliveryking.order;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.payment.repository.OrderRepository;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.king.deliveryking.order.entity.Order;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) {
        User user = userRepository.save(new User("user", "1234", "email@naver.com", UserRole.CUSTOMER));

        // id=1, PAID 상태 (취소 가능)
        orderRepository.save(new Order(user.getId(), 30000));

        // id=2, SHIPPED 상태 (취소 불가)
        Order shippedOrder = new Order(user.getId(), 50000);
        shippedOrder.changeStatus(OrderStatus.SHIPPED);
        orderRepository.save(shippedOrder);
    }
}