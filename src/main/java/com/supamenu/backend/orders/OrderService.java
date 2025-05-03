package com.supamenu.backend.orders;

import com.supamenu.backend.auth.AuthService;
import com.supamenu.backend.commons.exceptions.BadRequestException;
import com.supamenu.backend.orders.dtos.OrderDto;
import com.supamenu.backend.orders.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrdersOfCustomer(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrderByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId){
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(() -> new BadRequestException("Order Not Found."));

        var user = authService.getCurrentUser();

        if(!order.isPlaceBy(user)){
            throw new AccessDeniedException("You are not allowed to access this order.");
        }

        return orderMapper.toDto(order);
    }
}
