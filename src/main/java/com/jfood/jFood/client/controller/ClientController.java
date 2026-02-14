package com.jfood.jFood.client.controller;

import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;
import com.jfood.jFood.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseClientDto createClient(@Valid @RequestBody CreateClientDto createDto) {
        return clientService.createClient(createDto);
    }

    @PatchMapping("/{clientId}")
    public ResponseClientDto updateClient(@PathVariable Long clientId,
                                          @RequestBody UpdateClientDto updateDto) {
        return clientService.updateClient(clientId, updateDto);
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }

    @GetMapping
    public List<ResponseClientDto> getClients() {
        return clientService.getClients();
    }

}
