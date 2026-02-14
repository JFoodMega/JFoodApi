package com.jfood.jFood.dish.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateDishDTO {
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private String weightVolume;
    private Bool IsActive;
}