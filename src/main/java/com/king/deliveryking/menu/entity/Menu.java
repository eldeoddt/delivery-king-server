package com.king.deliveryking.menu.entity;

import com.king.deliveryking.global.entity.BaseEntity;
import com.king.deliveryking.menu.MenuStatus;
import com.king.deliveryking.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuStatus status = MenuStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void update(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Builder
    public Menu(String name, String description, Integer price, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.user = user;
    }

    public void delete() {
        this.status = MenuStatus.DELETED;
    }
}
