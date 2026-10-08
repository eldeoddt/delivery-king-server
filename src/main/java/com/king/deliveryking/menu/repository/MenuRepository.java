package com.king.deliveryking.menu.repository;

import com.king.deliveryking.menu.MenuStatus;
import com.king.deliveryking.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByStatus(MenuStatus menuStatus);

    Optional<Menu> findByIdAndStatus(Long id, MenuStatus status);
}
