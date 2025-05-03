package com.supamenu.backend.restaurants.dtos;

import java.util.UUID;

public record RestaurantResponseDto(
        UUID id,
        String name,
        String contactNumber,
        String ownerName,
        String ownerPhoneNumber,
        String ownerEmail,
        String type,
        String cuisineType,
        String openingHours,
        String imageUrl,
        String location
) {
}
