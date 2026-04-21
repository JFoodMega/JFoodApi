package com.jfood.jFood.signIn.controller;

import com.jfood.jFood.signIn.dto.LoginRequestDto;
import com.jfood.jFood.signIn.dto.LoginResponseDto;
import com.jfood.jFood.signIn.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }
}
