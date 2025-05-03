package com.supamenu.backend.carts;

import com.supamenu.backend.carts.dtos.AddItemToCartRequest;
import com.supamenu.backend.carts.dtos.CartDto;
import com.supamenu.backend.carts.dtos.CartItemDto;
import com.supamenu.backend.commons.generic_api_response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/carts")
@Tag(name = "Carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    ResponseEntity<ApiResponse<CartDto>> createCart(UriComponentsBuilder uriBuilder){
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.id()).toUri();
        return ResponseEntity.created(uri).body(ApiResponse.success("Cart created successfully", cartDto));
    }

    @GetMapping("/{cartId}")
    ResponseEntity<ApiResponse<CartDto>> getCart(@PathVariable("cartId") Long cartId){
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(ApiResponse.success("Cart retrieved successfully", cartDto));
    }


    @PostMapping("/{cartId}/items")
    ResponseEntity<ApiResponse<CartItemDto>> addToCart(@PathVariable Long cartId, @RequestBody AddItemToCartRequest addItemToCartRequest){
        var cartItemDto = cartService.addToCart(cartId, addItemToCartRequest.menuItemId());
        return ResponseEntity.ok(ApiResponse.success("Item added to cart successfully", cartItemDto));
    }

    @PatchMapping("/{cartId}/items/{menuItemId}")
    ResponseEntity<ApiResponse<CartItemDto>> updateItem(
            @PathVariable("cartId") Long cartId,
            @PathVariable("menuItemId") Long menuItemId
    ){
        var cartItemDto = cartService.updateItem(cartId, menuItemId, 1);
        return ResponseEntity.ok(ApiResponse.success("Item updated successfully", cartItemDto));
    }

    @DeleteMapping("/{cartId}/items/{menuItemId}")
    ResponseEntity<ApiResponse<?>> removeItem(
            @PathVariable("cartId") Long cartId,
            @PathVariable("menuItemId") Long menuItemId
    ){
        cartService.removeItem(cartId, menuItemId);
        return ResponseEntity.ok(ApiResponse.success("Item removed successfully", null));
    }

    @DeleteMapping("/{cartId}")
    ResponseEntity<ApiResponse<?>> clearCart(@PathVariable("cartId") Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared successfully", null));
    };

}
