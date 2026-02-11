package com.jfood.jFood.service;

import com.jfood.jFood.dto.request.AuthRequest;
import com.jfood.jFood.dto.request.RegisterRequest;
import com.jfood.jFood.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    AuthResponse register(RegisterRequest request);

    AuthResponse refreshToken(String refreshToken);
}

