package com.jfood.jFood.service.impl;

import com.jfood.jFood.dto.request.CreateClerkRequest;
import com.jfood.jFood.dto.request.UpdateClerkRequest;
import com.jfood.jFood.dto.response.UserResponse;
import com.jfood.jFood.entity.Role;
import com.jfood.jFood.entity.User;
import com.jfood.jFood.exception.BadRequestException;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.repository.UserRepository;
import com.jfood.jFood.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createClerk(CreateClerkRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new BadRequestException("Пользователь с таким логином уже существует");
        }
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(Role.valueOf(request.getRole()));
        user.setIsWork(true);
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateClerk(UUID id, UpdateClerkRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole()));
        }
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteClerk(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllClerks() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.COURIER || u.getRole() == Role.MODERATOR)
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.CLIENT)
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getPhone(),
                user.getRole().name(),
                user.getRole().getEmoji(),
                user.getIsWork(),
                null
        );
    }
}

