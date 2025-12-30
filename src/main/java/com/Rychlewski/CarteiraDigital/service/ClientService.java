package com.Rychlewski.CarteiraDigital.service;

import ch.qos.logback.core.net.server.Client;
import com.Rychlewski.CarteiraDigital.dto.client.ClientResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.client.CreateClientDTO;
import com.Rychlewski.CarteiraDigital.entity.ClientEntity;
import com.Rychlewski.CarteiraDigital.exception.ConflictException;
import com.Rychlewski.CarteiraDigital.exception.ResourceNotFoundException;
import com.Rychlewski.CarteiraDigital.mapper.ClientMapper;
import com.Rychlewski.CarteiraDigital.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO createClient(CreateClientDTO dto) {
        if (clientRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new ConflictException("Cliente com o " + dto.getCpf() + " já existe");
        }
        if (clientRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Cliente com o " + dto.getEmail() + " já existe");
        }
        ClientEntity client = ClientMapper.toEntity(dto);
        System.out.println("CPF bruto: [" + dto.getCpf() + "]");
        System.out.println("CPF length: " + dto.getCpf().length());

        for (char c : dto.getCpf().toCharArray()) {
            System.out.println((int) c);
        }
        ClientEntity savedClient = clientRepository.save(client);
        return ClientMapper.toResponse(savedClient);
    }

    public ClientResponseDTO getClientByCpf(String cpf) {
        Optional<ClientEntity> clientEntity = clientRepository.findByCpf(cpf);
        return clientEntity.map(ClientMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    public ClientResponseDTO getClientById(Long id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        return client.map(ClientMapper::toResponse).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }
}
