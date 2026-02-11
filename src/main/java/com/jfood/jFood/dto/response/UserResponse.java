package com.jfood.jFood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String login;
    private String name;
    private String phone;
    private String role;
    private String roleEmoji;
    private Boolean isWork;
    private List<AddressResponse> addresses;
}

