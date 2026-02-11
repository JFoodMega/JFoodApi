package com.jfood.jFood.service;

import com.jfood.jFood.dto.request.OrderRequest;
import com.jfood.jFood.dto.request.UpdateStatusRequest;
import com.jfood.jFood.dto.response.OrderResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderResponse> getUserOrders(UserDetails userDetails);

    OrderResponse getOrder(UUID id);

    OrderResponse createOrder(OrderRequest request, UserDetails userDetails);

    OrderResponse updateOrderStatus(UUID id, UpdateStatusRequest request);

    List<OrderResponse> getAllOrders();
}

