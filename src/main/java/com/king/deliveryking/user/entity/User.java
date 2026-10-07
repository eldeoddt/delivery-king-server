package com.king.deliveryking.user.entity;

import com.king.deliveryking.global.common.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_users") // 충돌로 인해 p_users
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private int failedLoginCount = 0;

    @Enumerated(EnumType.STRING)
    private UserRole status;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}