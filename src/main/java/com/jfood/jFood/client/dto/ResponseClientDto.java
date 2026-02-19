package com.jfood.jFood.client.dto;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.model.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ResponseClientDto {
    private Long id;
    @NotBlank
    private String phone;
    @NotBlank
    private String login;
    @NotBlank
    private String name;

    private List<AddressDto> addresses = new ArrayList<>();

    //private List<Order> orders = new ArrayList<>();
}
