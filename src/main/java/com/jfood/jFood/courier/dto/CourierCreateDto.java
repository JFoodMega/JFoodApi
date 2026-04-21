package com.jfood.jFood.courier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierCreateDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    private String name;
}
