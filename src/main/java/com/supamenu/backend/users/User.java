package com.supamenu.backend.users;

import com.supamenu.backend.carts.Cart;
import com.supamenu.backend.orders.Order;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email_unq", columnList = "email", unique = true),
        @Index(name = "idx_user_phonenumber_unq", columnList = "phoneNumber", unique = true),
        @Index(name = "idx_user_nationalid_unq", columnList = "nationalId", unique = true)
})
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 16)
    private String nationalId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean enabled = false;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
