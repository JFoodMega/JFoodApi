package com.jfood.jFood.address.controller;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;
import com.jfood.jFood.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;


    @PatchMapping("/{id}")
    public AddressDto update(@PathVariable Long id, @RequestBody @Valid UpdateAddressDto dto) {
        return addressService.updateAddress(id, dto);
    }

    @GetMapping("/{id}")
    public AddressDto getById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @GetMapping
    public List<AddressDto> getAll() {
        return addressService.getAllAddresses();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
