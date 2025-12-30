package com.Rychlewski.CarteiraDigital.controller;

import com.Rychlewski.CarteiraDigital.dto.client.ClientResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.client.CreateClientDTO;
import com.Rychlewski.CarteiraDigital.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClientResponseDTO> getClientByCpf(@PathVariable String cpf) {
        ClientResponseDTO client = clientService.getClientByCpf(cpf);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        ClientResponseDTO client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/create")
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Valid CreateClientDTO dto) {
        ClientResponseDTO client = clientService.createClient(dto);
        return ResponseEntity.status(201).body(client);
    }
}
