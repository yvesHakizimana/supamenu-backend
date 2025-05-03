package com.supamenu.backend.carts;

import com.supamenu.backend.carts.dtos.CartDto;
import com.supamenu.backend.carts.dtos.CartItemDto;
import com.supamenu.backend.carts.mappers.CartMapper;
import com.supamenu.backend.commons.exceptions.BadRequestException;
import com.supamenu.backend.menus.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final MenuItemRepository menuItemRepository;

    CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    CartItemDto addToCart(Long cartId, Long menuItemId){
        var cart = cartRepository.findCartWithItems(cartId).orElseThrow(() -> new BadRequestException("The cart requested was not found."));
        var menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new BadRequestException("The menu item requested was not found."));
        var cartItem = cart.addItem(menuItem);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    CartDto getCart(Long CartId){
        var cart = cartRepository.findCartWithItems(CartId).orElseThrow(() -> new BadRequestException("The cart requested was not found."));

        return cartMapper.toDto(cart);
    }

    CartItemDto updateItem(Long cartId, Long menuItemId, Integer quantity){
        var cart = cartRepository.findCartWithItems(cartId).orElseThrow(() -> new BadRequestException("The cart requested was not found."));

        var cartItem = cart.getItem(menuItemId);
        if(cartItem == null)
            throw new BadRequestException("The cart item requested was not found.");

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    void removeItem(Long cartId, Long menuItemId){
        var cart = cartRepository.findCartWithItems(cartId).orElseThrow(() -> new BadRequestException("The cart requested was not found."));
        cart.removeItem(menuItemId);
        cartRepository.save(cart);
    }

    void clearCart(Long cartId){
        var cart = cartRepository.findCartWithItems(cartId).orElseThrow(() -> new BadRequestException("The cart requested was not found."));

        cart.clear();

        cartRepository.save(cart);
    }

}
