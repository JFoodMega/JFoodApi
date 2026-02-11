package com.jfood.jFood.service;

import com.jfood.jFood.dto.request.CreateAddressRequest;
import com.jfood.jFood.dto.request.UpdateUserRequest;
import com.jfood.jFood.dto.response.AddressResponse;
import com.jfood.jFood.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserService {

    UserResponse getUserProfile(UserDetails userDetails);

    UserResponse updateProfile(UpdateUserRequest request, UserDetails userDetails);

    AddressResponse addAddress(CreateAddressRequest request, UserDetails userDetails);

    void deleteAddress(UUID addressId, UserDetails userDetails);
}

