package com.king.deliveryking.menu.repository;

import com.king.deliveryking.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
