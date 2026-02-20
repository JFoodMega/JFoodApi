package com.jfood.jFood.dish.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishDto {
    @NotBlank
    private String name;
    private Integer price;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String weightVolume;
}