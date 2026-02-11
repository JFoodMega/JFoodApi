package com.jfood.jFood.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    @NotEmpty(message = "Заказ должен содержать блюда")
    private Map<UUID, Integer> dishes; // dishId: quantity
}

