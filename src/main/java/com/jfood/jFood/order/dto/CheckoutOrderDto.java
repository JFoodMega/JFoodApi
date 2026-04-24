package com.jfood.jFood.order.dto;

import com.jfood.jFood.order.model.DeliveryType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CheckoutOrderDto {
    @NotNull
    private Long addressId;
    @NotNull
    private DeliveryType deliveryType;
    private LocalDateTime deliveryTime;
}