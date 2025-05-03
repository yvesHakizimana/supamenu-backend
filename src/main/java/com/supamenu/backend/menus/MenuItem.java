package com.supamenu.backend.menus;

import com.supamenu.backend.carts.CartItem;
import com.supamenu.backend.orders.OrderItem;
import com.supamenu.backend.restaurants.Restaurant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MenuItem {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem")
    private List<OrderItem> orderItems =  new ArrayList<>();

    @OneToMany(mappedBy = "menuItem")
    private List<CartItem> cartItems = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
