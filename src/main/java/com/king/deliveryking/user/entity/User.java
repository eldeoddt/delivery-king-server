package com.king.deliveryking.user.entity;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "p_users") // 충돌로 인해 p_users
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String username, String password, String email,
                UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}