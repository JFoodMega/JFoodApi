package com.jfood.jFood.dish.dto;

import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.DishType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateDishDto {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer price;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String weightVolume;

    @NotNull
    @Min(0)
    private Integer calories;

    @NotNull
    private CuisineType cuisineType;

    @NotNull
    private DishType dishType;

    @NotNull
    private List<CreateDishExtraDto> extras = new ArrayList<>();
}