package com.jfood.jFood.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный email")
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 4, max = 10, message = "Пароль должен быть от 4 до 10 символов")
    private String password;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(
            regexp = "^\\+7\\s?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
            message = "Некорректный номер телефона"
    )
    private String phone;
}

