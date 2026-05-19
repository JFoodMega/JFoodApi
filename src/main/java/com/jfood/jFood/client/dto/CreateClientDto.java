package com.jfood.jFood.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientDto {
    @NotBlank
    @Size(min = 2, max = 20, message = "Логин должен быть от 2 до 20 символов")
    private String login;

    @NotBlank
    @Size(min = 2, max = 20, message = "ФИО должно быть от 2 до 20 символов")
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @Size(min = 6, max = 15, message = "Пароль должен быть от 6 до 15 символов")
    private String password;
}
