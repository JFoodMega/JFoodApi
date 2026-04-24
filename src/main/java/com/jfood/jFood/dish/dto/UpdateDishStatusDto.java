package com.jfood.jFood.dish.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDishStatusDto {
    @NotNull
    private Boolean isActive;
}