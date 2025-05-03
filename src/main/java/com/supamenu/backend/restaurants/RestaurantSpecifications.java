package com.supamenu.backend.restaurants;

import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecifications {

    static Specification<Restaurant> withLocation(String location) {
        return (root, query, cb) ->
                location == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }


    static Specification<Restaurant> withCuisineType(RestaurantCuisineType cuisineType) {
        return (root, query, cb) ->
                cuisineType == null ? cb.conjunction() :
                        cb.equal(root.get("cuisineType"), cuisineType);
    }

    static Specification<Restaurant> withRestaurantType(RestaurantType restaurantType) {
        return (root, query, cb) ->
                restaurantType == null ? cb.conjunction() :
                        cb.equal(root.get("type"), restaurantType);
    }
}
