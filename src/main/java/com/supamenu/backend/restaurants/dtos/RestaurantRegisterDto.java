package com.supamenu.backend.restaurants.dtos;

import com.supamenu.backend.commons.validation.ValidEnum;
import com.supamenu.backend.commons.validation.ValidRwandaId;
import com.supamenu.backend.commons.validation.ValidRwandanPhoneNumber;
import com.supamenu.backend.restaurants.RestaurantCuisineType;
import com.supamenu.backend.restaurants.RestaurantType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record RestaurantRegisterDto(
        @NotBlank(message = "Restaurant name is required")
        String name,

        @NotBlank(message = "Restaurant location is required")
        @Size(min = 5, max = 20, message = "Location must be between 5 and 20 characters long")
        String location,

        @NotBlank(message = "Restaurant Contact number is required")
        @ValidRwandanPhoneNumber
        String contactNumber,

        @NotBlank(message = "Restaurant Owner name is required")
        @Size(min = 3, max = 50, message = "Owner name must be between 2 and 50 characters long")
        String ownerName,

        @NotBlank(message = "Restaurant Owner phone number is required")
        @ValidRwandanPhoneNumber
        String ownerPhoneNumber,

        @NotBlank(message = "Restaurant Owner email is required")
        @Email
        String ownerEmail,

        @NotBlank(message = "Restaurant type is required")
        @ValidEnum(enumClass = RestaurantType.class)
        String restaurantType,

        @NotBlank(message = "Restaurant cuisine type is required")
        @ValidEnum(enumClass = RestaurantCuisineType.class)
        String cuisineType,

        @NotBlank(message = "Restaurant opening hours are required")
        String openingHours,

        @NotNull(message = "Menu items list is required.")
        @Size(min = 1, message = "At leat one menu item is required.")
        @Valid
        @ModelAttribute
        List<MenuItemRegisterDto> menuItems,

        MultipartFile image
) {

}
