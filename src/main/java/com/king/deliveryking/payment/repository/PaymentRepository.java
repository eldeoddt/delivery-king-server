package com.king.deliveryking.payment.repository;

import com.king.deliveryking.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
