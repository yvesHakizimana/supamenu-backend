package com.supamenu.backend.orders.mappers;

import com.supamenu.backend.orders.Order;
import com.supamenu.backend.orders.dtos.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
