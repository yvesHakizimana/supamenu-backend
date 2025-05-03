package com.supamenu.backend.restaurants.dtos;

import com.supamenu.backend.commons.validation.ValidEnum;
import com.supamenu.backend.menus.MenuCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record MenuItemRegisterDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @ValidEnum(enumClass = MenuCategory.class)
        String category,

        MultipartFile image
) {
}
