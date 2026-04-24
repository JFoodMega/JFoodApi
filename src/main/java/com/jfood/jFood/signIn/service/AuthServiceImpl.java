package com.jfood.jFood.signIn.service;

import com.jfood.jFood.client.mapper.ClientMapper;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.courier.mapper.CourierMapper;
import com.jfood.jFood.courier.model.Courier;
import com.jfood.jFood.courier.repository.CourierRepository;
import com.jfood.jFood.moderator.mapper.ModeratorMapper;
import com.jfood.jFood.moderator.model.Moderator;
import com.jfood.jFood.moderator.repository.ModeratorRepository;
import com.jfood.jFood.signIn.dto.LoginRequestDto;
import com.jfood.jFood.signIn.dto.LoginResponseDto;
import com.jfood.jFood.signIn.dto.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final CourierRepository courierRepository;
    private final ModeratorRepository moderatorRepository;
    private final ClientMapper clientMapper;
    private final CourierMapper courierMapper;
    private final ModeratorMapper moderatorMapper;

    @Value("${admin.login}")
    private String adminLogin;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        if (dto.getLogin().equals(adminLogin)) {
            if (!dto.getPassword().equals(adminPassword)) {
                throw new IllegalArgumentException("Неверный пароль");
            }
            return new LoginResponseDto(Role.ADMIN, null);
        }

        Optional<Client> client = clientRepository.findByLogin(dto.getLogin());
        if (client.isPresent()) {
            if (!client.get().getPassword().equals(dto.getPassword())) {
                throw new IllegalArgumentException("Неверный пароль");
            }
            return new LoginResponseDto(Role.CLIENT, clientMapper.mapClientToResponseClientDto(client.get()));
        }

        Optional<Courier> courier = courierRepository.findByLogin(dto.getLogin());
        if (courier.isPresent()) {
            if (!courier.get().getPassword().equals(dto.getPassword())) {
                throw new IllegalArgumentException("Неверный пароль");
            }
            return new LoginResponseDto(Role.COURIER, courierMapper.toResponseDto(courier.get()));
        }

        Optional<Moderator> moderator = moderatorRepository.findByLogin(dto.getLogin());
        if (moderator.isPresent()) {
            if (!moderator.get().getPassword().equals(dto.getPassword())) {
                throw new IllegalArgumentException("Неверный пароль");
            }
            return new LoginResponseDto(Role.MODERATOR, moderatorMapper.toResponseDto(moderator.get()));
        }

        throw new EntityNotFoundException("Пользователь не найден");
    }
}
