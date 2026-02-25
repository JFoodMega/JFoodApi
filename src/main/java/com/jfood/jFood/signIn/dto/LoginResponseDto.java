package com.jfood.jFood.signIn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private Role role;
    private Object profile;
}