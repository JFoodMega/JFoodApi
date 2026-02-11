package com.jfood.jFood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private UUID id;
    private String createdAt;
    private String address;
    private Map<DishResponse, Integer> dishes;
    private Integer price;
    private String status;
    private String statusColor;
    private UserResponse user;
    private UserResponse courier;
}

