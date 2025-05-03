package com.supamenu.backend.carts.mappers;

import com.supamenu.backend.carts.Cart;
import com.supamenu.backend.carts.CartItem;
import com.supamenu.backend.carts.dtos.CartDto;
import com.supamenu.backend.carts.dtos.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items", source= "items")
    @Mapping(target = "totalPrice", expression = "java(cart.getCartTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
