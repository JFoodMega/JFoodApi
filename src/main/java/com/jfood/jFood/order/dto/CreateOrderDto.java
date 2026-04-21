package com.jfood.jFood.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderDto {
    @NotNull
    private Long clientId;
    @NotEmpty
    private List<Long> dishIds;
}
