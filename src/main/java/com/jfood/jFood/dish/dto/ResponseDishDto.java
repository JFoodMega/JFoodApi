package com.jfood.jFood.dish.dto;

import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.DishType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDishDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String imageUrl;
    private String weightVolume;
    private Integer calories;
    private CuisineType cuisineType;
    private List<ResponseDishExtraDto> extras;
    private DishType dishType;
    private Boolean isActive;
}