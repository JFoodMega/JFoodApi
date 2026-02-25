package com.jfood.jFood.order.service;

import com.jfood.jFood.order.dto.CreateOrderDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.dto.UpdateOrderStatusDto;
import com.jfood.jFood.order.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    ResponseOrderDto createOrder(CreateOrderDto dto);

    ResponseOrderDto getById(Long orderId);

    Page<ResponseOrderDto> getAll(Pageable pageable);

    Page<ResponseOrderDto> getByClientId(Long clientId, Pageable pageable);

    ResponseOrderDto updateStatus(Long orderId, OrderStatus status);

    void delete(Long orderId);

    ResponseOrderDto updateStatus(Long orderId, UpdateOrderStatusDto dto);
}
