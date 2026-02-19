package com.jfood.jFood.dish.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDishDto {

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
}