package com.Rychlewski.CarteiraDigital.controller;

import com.Rychlewski.CarteiraDigital.dto.transaction.DepositDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.TransactionResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.TransferDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.WithdrawDTO;
import com.Rychlewski.CarteiraDigital.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody @Valid DepositDTO dto) {
        TransactionResponseDTO deposit = transactionService.deposit(dto);
        return ResponseEntity.status(201).body(deposit);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdraw(@RequestBody @Valid WithdrawDTO dto) {
        TransactionResponseDTO withdraw = transactionService.withdraw(dto);
        return ResponseEntity.status(201).body(withdraw);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@RequestBody @Valid TransferDTO dto) {
        TransactionResponseDTO transfer = transactionService.transfer(dto);
        return ResponseEntity.status(201).body(transfer);
    }


    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long transactionId) {
        TransactionResponseDTO transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/statement/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getStatement(@PathVariable Long accountId) {
        List<TransactionResponseDTO> statement = transactionService.getStatement(accountId);
        return ResponseEntity.status(200).body(statement);
    }
}
