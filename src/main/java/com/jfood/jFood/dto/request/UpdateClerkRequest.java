package com.jfood.jFood.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClerkRequest {

    @Size(min = 2, max = 50)
    private String name;

    @Pattern(
            regexp = "^\\+7\\s?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$",
            message = "Некорректный номер телефона"
    )
    private String phone;

    private String role;
}

