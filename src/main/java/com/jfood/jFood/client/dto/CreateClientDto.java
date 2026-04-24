package com.jfood.jFood.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientDto {
    @NotBlank
    @Email
    private String login;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
}
