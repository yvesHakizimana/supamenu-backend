package com.supamenu.backend.restaurants.mappers;

import com.supamenu.backend.restaurants.Restaurant;
import com.supamenu.backend.restaurants.dtos.RestaurantRegisterDto;
import com.supamenu.backend.restaurants.dtos.RestaurantResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", expression = "java(com.supamenu.backend.restaurants.RestaurantType.valueOf(restaurantDTO.restaurantType().toUpperCase()))")
    @Mapping(target = "cuisineType", expression = "java(com.supamenu.backend.restaurants.RestaurantCuisineType.valueOf(restaurantDTO.cuisineType().toUpperCase()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "image", target = "imageUrl", ignore = true)
    @Mapping(source = "menuItems", target = "menuItems", ignore = true)
    Restaurant toEntity(RestaurantRegisterDto restaurantDTO);

    @Mapping(target = "type", expression = "java(restaurant.getType().toString())")
    @Mapping(target = "cuisineType", expression = "java(restaurant.getCuisineType().toString())")
    RestaurantResponseDto toDto(Restaurant restaurant);
}
