package com.supamenu.backend.restaurants;

import com.supamenu.backend.commons.generic_api_response.ApiResponse;
import com.supamenu.backend.menus.MenuCategory;
import com.supamenu.backend.menus.dtos.MenuItemDto;
import com.supamenu.backend.menus.mappers.MenuItemMapper;
import com.supamenu.backend.restaurants.dtos.RestaurantRegisterDto;
import com.supamenu.backend.restaurants.dtos.RestaurantResponseDto;
import com.supamenu.backend.restaurants.mappers.RestaurantMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final MenuItemMapper menuItemMapper;


    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<ApiResponse<?>> registerNewRestaurant(@Valid @ModelAttribute RestaurantRegisterDto dto){
        restaurantService.createRestaurant(dto);
        return ResponseEntity.ok(ApiResponse.success("Restaurant registered successfully", null));
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<RestaurantResponseDto>>> getAllRestaurants(
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "cuisineType", required = false) RestaurantCuisineType cuisineType,
            @RequestParam(value = "type", required = false) RestaurantType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        var pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);
        var restaurants = restaurantService.getAllRestaurants(pageable, location, cuisineType, type);
        return ResponseEntity.ok(ApiResponse.success("Restaurants retrieved successfully",
                restaurants.getContent().stream().map(restaurantMapper::toDto).toList(),
                ApiResponse.fromSpringPage(restaurants)));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<RestaurantResponseDto>> getRestaurantById(@PathVariable("id") @UUID String id){
        var restaurant = restaurantService.getRestaurantById(java.util.UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success("Restaurant retrieved successfully", restaurant));
    }

    @GetMapping("/{restaurantId}/menu-items")
    ResponseEntity<ApiResponse<List<MenuItemDto>>> getAllMenuItems(
            @PathVariable("restaurantId") @UUID String restaurantId,
            @RequestParam(value = "category", required = false) MenuCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        var pageable = PageRequest.of(page, size);
        var menuItems = restaurantService.getAllMenuItemsByRestaurantAndCategory(java.util.UUID.fromString(restaurantId), category, pageable);
        return ResponseEntity.ok(ApiResponse.success("Menu items retrieved successfully",
                menuItems.getContent().stream().map(menuItemMapper::toDto).toList(),
                ApiResponse.fromSpringPage(menuItems)));
    }


}
