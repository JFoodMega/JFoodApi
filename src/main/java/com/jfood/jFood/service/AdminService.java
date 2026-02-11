package com.jfood.jFood.service;

import com.jfood.jFood.dto.request.CreateClerkRequest;
import com.jfood.jFood.dto.request.UpdateClerkRequest;
import com.jfood.jFood.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    UserResponse createClerk(CreateClerkRequest request);

    UserResponse updateClerk(UUID id, UpdateClerkRequest request);

    void deleteClerk(UUID id);

    List<UserResponse> getAllClerks();

    List<UserResponse> getAllUsers();
}

