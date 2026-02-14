package com.jfood.jFood.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClientDto {
    private String name;
    private String phone;
    private String login;
    //private List<Address> addresses = new ArrayList<>();
}
