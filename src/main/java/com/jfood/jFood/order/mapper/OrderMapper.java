package com.jfood.jFood.order.mapper;

import com.jfood.jFood.order.dto.CreateOrderDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "courierId", source = "courier.id")
    @Mapping(target = "moderatorId", source = "moderator.id")
    @Mapping(target = "addressId", source = "address.id")
    ResponseOrderDto toResponseDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "moderator", ignore = true)
    @Mapping(target = "assignedAt", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "deliveryTime", ignore = true)
    @Mapping(target = "deliveryType", ignore = true)
    Order toEntity(CreateOrderDto dto);
}