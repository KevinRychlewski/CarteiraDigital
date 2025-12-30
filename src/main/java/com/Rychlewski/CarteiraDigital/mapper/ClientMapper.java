package com.Rychlewski.CarteiraDigital.mapper;

import com.Rychlewski.CarteiraDigital.dto.client.ClientResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.client.CreateClientDTO;
import com.Rychlewski.CarteiraDigital.entity.ClientEntity;

public class ClientMapper {

    public static ClientEntity toEntity(CreateClientDTO dto) {
        ClientEntity client = new ClientEntity();
        client.setName(dto.getName());
        client.setCpf(dto.getCpf());
        client.setEndereco(dto.getEndereco());
        client.setEmail(dto.getEmail());
        return client;
    }

    public static ClientResponseDTO toResponse(ClientEntity entity) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCpf(entity.getCpf());
        dto.setEndereco(entity.getEndereco());
        dto.setEmail(entity.getEmail());
        return dto;
    }

}
