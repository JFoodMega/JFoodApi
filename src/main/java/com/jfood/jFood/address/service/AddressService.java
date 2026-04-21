package com.jfood.jFood.address.service;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(UpdateAddressDto dto);

    AddressDto updateAddress(Long id, UpdateAddressDto dto);

    AddressDto getAddressById(Long id);

    List<AddressDto> getAllAddresses();

    void deleteAddress(Long id);
}
