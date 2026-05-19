package com.jfood.jFood.dish.dto;


import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.DishType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateDishDto {
    @Size(max = 40, message = "Название блюда не должно превышать 40 символов")
    private String name;
    @Positive
    private Integer price;
    private String description;
    private String imageUrl;
    private String weightVolume;
    @Min(0)
    private Integer calories;
    private CuisineType cuisineType;
    private DishType dishType;
    private List<CreateDishExtraDto> extras;
}