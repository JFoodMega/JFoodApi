package com.jfood.jFood.moderator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModeratorDto {
    private Long id;
    @NotBlank
    private String phone;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
}
