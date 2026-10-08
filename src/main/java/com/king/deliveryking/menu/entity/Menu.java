package com.king.deliveryking.menu.entity;

import com.king.deliveryking.global.entity.BaseEntity;
import com.king.deliveryking.menu.MenuStatus;
import com.king.deliveryking.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    public void update(
            @NotBlank String name, @NotNull Integer price, @Size(max = 255) String description
    ) {
    }

    @Builder
    public Menu(String name, String description, Integer price, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.user = user;
    }
}
