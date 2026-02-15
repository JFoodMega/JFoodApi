package com.jfood.jFood.dish.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter

public class ResponseDishDTO {
    @NotBlank
    private String name;
    @NotBlank
    private Integer price;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String weightVolume;
    @NotBlank
    private Boolean IsActive;
}