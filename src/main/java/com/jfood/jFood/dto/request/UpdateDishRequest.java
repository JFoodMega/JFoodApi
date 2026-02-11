package com.jfood.jFood.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishRequest {

    private String name;
    private String composition;

    @Min(0)
    private Integer ccal;

    @Min(1)
    private Integer weight;

    @Min(0)
    private Integer price;

    private String kitchenType;
    private String dishType;
}

