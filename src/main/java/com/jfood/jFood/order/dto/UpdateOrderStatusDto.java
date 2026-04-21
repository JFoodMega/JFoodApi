package com.jfood.jFood.order.dto;

import com.jfood.jFood.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDto {
    @NotNull
    private OrderStatus status;
}