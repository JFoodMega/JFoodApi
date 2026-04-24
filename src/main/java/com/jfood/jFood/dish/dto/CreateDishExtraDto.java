package com.jfood.jFood.dish.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishExtraDto {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer price;
}