package com.jfood.jFood.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDishRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String composition;

    @NotNull
    @Min(0)
    private Integer ccal;

    @NotNull
    @Min(1)
    private Integer weight;

    @NotNull
    @Min(0)
    private Integer price;

    @NotBlank
    private String kitchenType;

    @NotBlank
    private String dishType;
}

