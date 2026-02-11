package com.jfood.jFood.service.impl;

import com.jfood.jFood.dto.request.CreateAddressRequest;
import com.jfood.jFood.dto.request.UpdateUserRequest;
import com.jfood.jFood.dto.response.AddressResponse;
import com.jfood.jFood.dto.response.UserResponse;
import com.jfood.jFood.entity.Address;
import com.jfood.jFood.entity.User;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.repository.AddressRepository;
import com.jfood.jFood.repository.UserRepository;
import com.jfood.jFood.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public UserResponse getUserProfile(UserDetails userDetails) {
        User user = getUserByLogin(userDetails.getUsername());
        return toUserResponse(user);
    }

    @Override
    public UserResponse updateProfile(UpdateUserRequest request, UserDetails userDetails) {
        User user = getUserByLogin(userDetails.getUsername());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public AddressResponse addAddress(CreateAddressRequest request, UserDetails userDetails) {
        User user = getUserByLogin(userDetails.getUsername());
        Address address = new Address();
        address.setName(request.getName());
        address.setUser(user);
        Address saved = addressRepository.save(address);
        return new AddressResponse(saved.getId(), saved.getName());
    }

    @Override
    public void deleteAddress(UUID addressId, UserDetails userDetails) {
        User user = getUserByLogin(userDetails.getUsername());
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Адрес не найден"));
        if (!address.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Адрес не найден");
        }
        addressRepository.delete(address);
    }

    private User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private UserResponse toUserResponse(User user) {
        List<Address> addresses = addressRepository.findByUser(user);
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getPhone(),
                user.getRole().name(),
                user.getRole().getEmoji(),
                user.getIsWork(),
                addresses.stream()
                        .map(a -> new AddressResponse(a.getId(), a.getName()))
                        .collect(Collectors.toList())
        );
    }
}

