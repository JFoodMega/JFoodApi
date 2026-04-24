package com.jfood.jFood.client.service;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;
import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.LogInClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    ResponseClientDto createClient(CreateClientDto createClientDto);

    Page<ResponseClientDto> getClients(String search, Pageable pageable);

    ResponseClientDto getClientById(Long clientId);

    void deleteClient(Long clientId);

    ResponseClientDto updateClient(Long clientId, UpdateClientDto updateDto);

    void deleteAddress(Long clientId, Long addressId);

    AddressDto updateClientAddress(Long clientId, Long addressId, UpdateAddressDto addressDto);

    AddressDto addAddress(Long clientId, UpdateAddressDto addressDto);

    List<AddressDto> getClientAddresses(Long clientId);

    ResponseClientDto login(LogInClientDto dto);

    Page<ResponseOrderDto> getClientOrders(Long clientId, Pageable pageable);
}