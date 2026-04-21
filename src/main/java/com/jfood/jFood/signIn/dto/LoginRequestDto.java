package com.jfood.jFood.signIn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}