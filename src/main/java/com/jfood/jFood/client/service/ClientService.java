package com.jfood.jFood.client.service;

import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;

import java.util.List;

public interface ClientService {
    ResponseClientDto createClient(CreateClientDto createClientDto);

    List<ResponseClientDto> getClients();

    void deleteClient(Long clientId);

    ResponseClientDto updateClient(Long clientId, UpdateClientDto updateDto);

}
