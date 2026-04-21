package com.jfood.jFood.client.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientDto {
    @NotBlank
    private String phone;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
