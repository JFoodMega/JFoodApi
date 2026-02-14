package com.jfood.jFood.dish.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class ResponseDishDTO {
    @NotBlanc
    private String name;
    @NotBlanc
    private BigDecimal price;
    @NotBlanc
    private String description;
    @NotBlanc
    private String imageUrl;
    @NotBlanc
    private String weightVolume;
    @NotBlanc
    private Bool IsActive;
}