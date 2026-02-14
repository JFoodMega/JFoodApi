package com.jfood.jFood.client.service;

import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;
import com.jfood.jFood.client.mapper.ClientMapper;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @Override
    @Transactional
    public ResponseClientDto createClient(CreateClientDto createClientDto){
        Client clientEntity = mapper.mapCreateClientDtoToClient(createClientDto);

        Client savedClient = clientRepository.save(clientEntity);

        return mapper.mapClientToResponseClientDto(savedClient);

    }

    @Override
    public List<ResponseClientDto> getClients(){
        List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map(mapper::mapClientToResponseClientDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Client with id=" + clientId + " was not found"));
        clientRepository.delete(client);
    }

    @Override
    @Transactional
    public ResponseClientDto updateClient(Long clientId, UpdateClientDto updateDto) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Client with id=" + clientId + " was not found"));

        mapper.updateClientFromDto(updateDto, client);

        Client savedClient = clientRepository.save(client);

        return mapper.mapClientToResponseClientDto(savedClient);
    }

}
