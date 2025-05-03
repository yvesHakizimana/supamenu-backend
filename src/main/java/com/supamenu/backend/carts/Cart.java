package com.supamenu.backend.carts;

import com.supamenu.backend.menus.MenuItem;
import com.supamenu.backend.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public BigDecimal getCartTotalPrice(){
        return items.stream()
                .map(item -> item.getMenuItem().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItem(Long menuItemId){
        return items.stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(MenuItem menuItem){
        var cartItem = getItem(menuItem.getId());
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setMenuItem(menuItem);
            cartItem.setQuantity(1);
            cartItem.setCart(this);
            items.add(cartItem);
        }
        return cartItem;
    }

    public void removeItem(Long menuItemId){
        var cartItem = getItem(menuItemId);
        if(cartItem != null){
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clear(){
        items.clear();
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
