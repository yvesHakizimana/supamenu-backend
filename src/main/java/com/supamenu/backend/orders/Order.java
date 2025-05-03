package com.supamenu.backend.orders;

import com.supamenu.backend.carts.Cart;
import com.supamenu.backend.restaurants.Restaurant;
import com.supamenu.backend.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus staus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    private TableNumber tableNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OrderItem> items;

    private BigDecimal totalPrice;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static Order fromCart(Cart cart, User customer){
        var order = new Order();
        order.setCustomer(customer);
        order.setStaus(OrderStatus.NEW);
        order.setTotalPrice(cart.getCartTotalPrice());

        cart.getItems().forEach(item -> {
            var orderItem = OrderItem.builder()
                    .menuItem(item.getMenuItem())
                    .quantity(item.getQuantity())
                    .order(order)
                    .build();
            order.items.add(orderItem);
        });

        return order;
    }

    public boolean isPlaceBy(User customer){
        return this.customer.equals(customer);
    }
}
