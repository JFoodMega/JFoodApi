package com.jfood.jFood.moderator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeratorCreateDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    private String name;
}
