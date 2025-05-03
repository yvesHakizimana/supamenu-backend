package com.supamenu.backend.restaurants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID>, JpaSpecificationExecutor<Restaurant> {
    Optional<Restaurant> existsByContactNumberOrOwnerEmailOrOwnerPhoneNumber(String contactNumber, String ownerEmail, String ownerPhoneNumber);

    Optional<Restaurant> findByLocationIgnoreCase(String location);
}
