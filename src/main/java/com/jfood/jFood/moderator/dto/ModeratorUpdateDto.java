package com.jfood.jFood.moderator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeratorUpdateDto {
    private String login;
    private String phone;
    private String name;
    private String password;
}