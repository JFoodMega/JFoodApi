package com.jfood.jFood.address.service;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;
import com.jfood.jFood.address.mapper.AddressMapper;
import com.jfood.jFood.address.model.Address;
import com.jfood.jFood.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddresServiceImpl implements AddressService{
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public AddressDto createAddress(UpdateAddressDto dto) {
        Address address = addressMapper.mapToAddress(dto);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.mapToAddressDto(savedAddress);
    }

    @Override
    @Transactional
    public AddressDto updateAddress(Long id, UpdateAddressDto dto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + id));

        addressMapper.updateAddressFromDto(dto, address);

        Address updatedAddress = addressRepository.save(address);
        return addressMapper.mapToAddressDto(updatedAddress);
    }

    @Override
    public AddressDto getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::mapToAddressDto)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::mapToAddressDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Address not found");
        }
        addressRepository.deleteById(id);
    }

}
