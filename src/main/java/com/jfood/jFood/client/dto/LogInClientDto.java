package com.jfood.jFood.client.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInClientDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
