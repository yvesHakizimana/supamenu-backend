package com.supamenu.backend.menus;

import com.supamenu.backend.restaurants.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class MenuItemSpecifications {

    public static Specification<MenuItem> withCategory(MenuCategory category) {
        return (root, query, cb) ->
                category == null ? cb.conjunction() :
                        cb.equal(root.get("category"), category);
    }

    public static Specification<MenuItem> withRestaurant(Restaurant restaurant) {
        return (root, query, cb) ->
                restaurant == null ? cb.conjunction() :
                        cb.equal(root.get("restaurant"), restaurant);
    }
}
