package com.supamenu.backend.orders;

import com.supamenu.backend.commons.generic_api_response.ApiResponse;
import com.supamenu.backend.orders.dtos.OrderDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders(){
        var orders = orderService.getAllOrdersOfCustomer();
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @GetMapping("/{orderId}")
    ResponseEntity<ApiResponse<OrderDto>> getOrder(Long orderId){
        var order = orderService.getOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order retrieved successfully", order));
    }
}
