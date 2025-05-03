package com.supamenu.backend.restaurants;

import com.supamenu.backend.menus.MenuItem;
import com.supamenu.backend.orders.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String contactNumber;
    private String ownerName;
    private String location;

    @Size(min = 10, max = 10)
    private String ownerPhoneNumber;

    @Email
    @Column(nullable = false, unique = true)
    private String ownerEmail;

    @Enumerated(EnumType.STRING)
    private RestaurantType type;

    @Enumerated(EnumType.STRING)
    private RestaurantCuisineType cuisineType;

    private String openingHours;

    @Column(nullable = false)
    private String imageUrl;


    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Order> orders = new LinkedHashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
