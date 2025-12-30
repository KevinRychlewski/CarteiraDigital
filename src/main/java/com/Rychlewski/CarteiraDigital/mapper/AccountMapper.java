package com.Rychlewski.CarteiraDigital.mapper;

import com.Rychlewski.CarteiraDigital.dto.account.AccountResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.account.CreateAccountDTO;
import com.Rychlewski.CarteiraDigital.entity.AccountEntity;
import com.Rychlewski.CarteiraDigital.entity.ClientEntity;

public class AccountMapper {

    public static AccountEntity toEntity(CreateAccountDTO dto, ClientEntity owner) {
        AccountEntity account = new AccountEntity();
        account.setAgencia(dto.getAgencia());
        account.setNumero(dto.getNumero());
        account.setAccountType(dto.getAccountType());
        account.setOwner(owner);
        return account;
    }

    public static AccountResponseDTO toResponse(AccountEntity entity) {
        AccountResponseDTO response = new AccountResponseDTO();
        response.setId(entity.getId());
        response.setAgencia(entity.getAgencia());
        response.setNumero(entity.getNumero());
        response.setAccountType(entity.getAccountType());
        response.setStatus(entity.getStatus());
        response.setSaldo(entity.getSaldo());
        return response;
    }

}
