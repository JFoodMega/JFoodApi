package com.jfood.jFood.dish.dto;


import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.DishType;
import jakarta.validation.constraints.Max;
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
    @Max(value = 99999, message = "Цена не должна превышать 99999 ₽")
    private Integer price;
    @Size(max = 300, message = "Описание не должно превышать 300 символов")
    private String description;
    private String imageUrl;
    @Size(max = 30, message = "Вес/объём не должен превышать 30 символов")
    private String weightVolume;
    @Min(1)
    @Max(value = 9999, message = "Калорийность не должна превышать 9999 ккал")
    private Integer calories;
    private CuisineType cuisineType;
    private DishType dishType;
    private List<CreateDishExtraDto> extras;
}