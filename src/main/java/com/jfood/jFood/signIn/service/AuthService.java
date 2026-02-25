package com.jfood.jFood.signIn.service;

import com.jfood.jFood.signIn.dto.LoginRequestDto;
import com.jfood.jFood.signIn.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto);
}
