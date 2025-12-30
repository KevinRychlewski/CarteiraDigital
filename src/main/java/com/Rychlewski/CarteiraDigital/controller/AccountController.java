package com.Rychlewski.CarteiraDigital.controller;

import com.Rychlewski.CarteiraDigital.dto.account.AccountResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.account.CreateAccountDTO;
import com.Rychlewski.CarteiraDigital.enums.AccountStatus;
import com.Rychlewski.CarteiraDigital.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody CreateAccountDTO dto) {
        AccountResponseDTO account = accountService.createAccount(dto);
        return ResponseEntity.status(201).body(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        AccountResponseDTO account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AccountResponseDTO>> getOwnerAccountsById(@PathVariable Long clientId) {
        List<AccountResponseDTO> accounts = accountService.getOwnerAccountById(clientId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}/status")
    public ResponseEntity<Boolean> isAccountActive(@PathVariable Long accountId) {
        boolean active = accountService.isAccountActive(accountId);
        return ResponseEntity.ok(active);
    }
}
