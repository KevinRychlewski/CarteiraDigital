package com.Rychlewski.CarteiraDigital.service;

import com.Rychlewski.CarteiraDigital.dto.account.AccountResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.account.CreateAccountDTO;
import com.Rychlewski.CarteiraDigital.entity.AccountEntity;
import com.Rychlewski.CarteiraDigital.entity.ClientEntity;
import com.Rychlewski.CarteiraDigital.enums.AccountStatus;
import com.Rychlewski.CarteiraDigital.exception.ConflictException;
import com.Rychlewski.CarteiraDigital.exception.ResourceNotFoundException;
import com.Rychlewski.CarteiraDigital.mapper.AccountMapper;
import com.Rychlewski.CarteiraDigital.repository.AccountRepository;
import com.Rychlewski.CarteiraDigital.repository.ClientRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public AccountResponseDTO createAccount(CreateAccountDTO dto) {
        if (accountRepository.findByAgenciaAndNumero(dto.getAgencia(), dto.getNumero()).isPresent()) {
            throw new ConflictException("Conta com essa agência e número já existe.");
        }
        ClientEntity client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
        AccountEntity account = AccountMapper.toEntity(dto, client);
        AccountEntity savedAccount = accountRepository.save(account);
        return AccountMapper.toResponse(savedAccount);
    }

    public AccountResponseDTO getAccountById(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
        return AccountMapper.toResponse(account);
    }

    public List<AccountResponseDTO> getOwnerAccountById(Long ownerId) {
        List<AccountEntity> accounts = accountRepository.findByOwnerId(ownerId);
        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("Conta não encontrada");

        }
        return accounts.stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    public boolean isAccountActive(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
        ;

        return account.getStatus() == AccountStatus.ATIVA;
    }
}
