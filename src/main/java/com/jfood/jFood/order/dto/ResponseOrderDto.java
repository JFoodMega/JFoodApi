package com.jfood.jFood.order.dto;

import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.order.model.DeliveryType;
import com.jfood.jFood.order.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResponseOrderDto {
    private Long id;
    private Long clientId;
    private Long courierId;
    private Long moderatorId;
    private OrderStatus status;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime assignedAt;
    private LocalDateTime deliveryTime;
    private DeliveryType deliveryType;
    private Long addressId;
    private List<ResponseDishDto> dishes;
}
