package com.supamenu.backend.restaurants;

import com.supamenu.backend.commons.exceptions.BadRequestException;
import com.supamenu.backend.commons.exceptions.NotFoundException;
import com.supamenu.backend.commons.generic_api_response.ApiResponse;
import com.supamenu.backend.file_storage.FileStorageService;
import com.supamenu.backend.menus.MenuCategory;
import com.supamenu.backend.menus.MenuItem;
import com.supamenu.backend.menus.MenuItemRepository;
import com.supamenu.backend.menus.MenuItemSpecifications;
import com.supamenu.backend.restaurants.dtos.RestaurantRegisterDto;
import com.supamenu.backend.restaurants.dtos.RestaurantResponseDto;
import com.supamenu.backend.restaurants.mappers.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FileStorageService fileStorageService;
    private final RestaurantMapper restaurantMapper;
    private final MenuItemRepository menuItemRepository;

    public static final String RESTAURANT_IMAGES_DIR = "restaurants";
    public static final String MENU_IMAGES_DIR = "menu_items";


    public void createRestaurant(RestaurantRegisterDto restaurantDto) {
        if(restaurantRepository.existsByContactNumberOrOwnerEmailOrOwnerPhoneNumber(
                restaurantDto.contactNumber(),
                restaurantDto.ownerEmail(),
                restaurantDto.ownerPhoneNumber()
        ).isPresent()){
            throw new BadRequestException("Restaurant with this contact number, email or phone number already exists");
        }

        var newRestaurant = restaurantMapper.toEntity(restaurantDto);

        var menuItems = restaurantDto.menuItems().stream().map(item ->
            MenuItem.builder()
                    .name(item.name())
                    .description(item.description())
                    .price(item.price())
                    .category(MenuCategory.valueOf(item.category().toUpperCase()))
                    .imageUrl(fileStorageService.storeFile(item.image(), MENU_IMAGES_DIR))
                    .restaurant(newRestaurant)
                    .build()
        ).toList();

        newRestaurant.setMenuItems(menuItems);

        newRestaurant.setImageUrl(fileStorageService.storeFile(restaurantDto.image(), RESTAURANT_IMAGES_DIR));

        restaurantRepository.save(newRestaurant);
    }
    
    RestaurantResponseDto getRestaurantById(UUID id){
        var restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with this id was not found"));

        return restaurantMapper.toDto(restaurant);
    }

    Page<Restaurant> getAllRestaurants(Pageable pageable, String location, RestaurantCuisineType cuisineType, RestaurantType type){

        Specification<Restaurant> spec = Specification
                .where(RestaurantSpecifications.withLocation(location))
                .and(RestaurantSpecifications.withCuisineType(cuisineType))
                .and(RestaurantSpecifications.withRestaurantType(type));

        return restaurantRepository.findAll(spec, pageable);
    }

    Page<MenuItem> getAllMenuItemsByRestaurantAndCategory(UUID restaurantId, MenuCategory category, Pageable pageable){
        var restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant with this id was not found"));

        Specification<MenuItem> spec = Specification
                .where(MenuItemSpecifications.withCategory(category))
                .and(MenuItemSpecifications.withRestaurant(restaurant));

        return menuItemRepository.findAll(spec, pageable);
    }

}
