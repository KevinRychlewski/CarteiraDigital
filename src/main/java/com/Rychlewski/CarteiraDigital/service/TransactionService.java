package com.Rychlewski.CarteiraDigital.service;

import com.Rychlewski.CarteiraDigital.dto.account.AccountResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.account.CreateAccountDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.DepositDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.TransactionResponseDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.TransferDTO;
import com.Rychlewski.CarteiraDigital.dto.transaction.WithdrawDTO;
import com.Rychlewski.CarteiraDigital.entity.AccountEntity;
import com.Rychlewski.CarteiraDigital.entity.TransactionEntity;
import com.Rychlewski.CarteiraDigital.enums.AccountStatus;
import com.Rychlewski.CarteiraDigital.enums.TransactionStatus;
import com.Rychlewski.CarteiraDigital.enums.TransactionType;
import com.Rychlewski.CarteiraDigital.exception.ResourceNotFoundException;
import com.Rychlewski.CarteiraDigital.mapper.TransactionMapper;
import com.Rychlewski.CarteiraDigital.repository.AccountRepository;
import com.Rychlewski.CarteiraDigital.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TransactionResponseDTO deposit(DepositDTO dto) {
        AccountEntity account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
        if (account.getStatus() != AccountStatus.ATIVA) {
            throw new IllegalArgumentException("A conta não está ativa.");
        }
        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        account.setSaldo(account.getSaldo().add(dto.getValor()));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSITO);
        transaction.setValue(dto.getValor());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setDescription(dto.getDescricao());
        transaction.setCurrency("BRL");

        TransactionEntity savedDeposit = transactionRepository.save(transaction);
        return TransactionMapper.toResponse(savedDeposit);
    }

    @Transactional
    public TransactionResponseDTO withdraw(WithdrawDTO dto) {
        AccountEntity account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
        if (account.getStatus() != AccountStatus.ATIVA) {
            throw new IllegalArgumentException("A conta não está ativa.");
        }
        if (account.getSaldo().compareTo(dto.getValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        account.setSaldo(account.getSaldo().subtract(dto.getValor()));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.SAQUE);
        transaction.setValue(dto.getValor());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setDescription(dto.getDescricao());
        transaction.setCurrency("BRL");

        TransactionEntity savedWithdraw = transactionRepository.save(transaction);
        return TransactionMapper.toResponse(savedWithdraw);
    }

    @Transactional
    public TransactionResponseDTO transfer(TransferDTO dto) {
        AccountEntity fromAccount = accountRepository.findById(dto.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta de origem não encontrada."));
        AccountEntity toAccount = accountRepository.findById(dto.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta de destino não encontrada."));
        if (fromAccount.getStatus() != AccountStatus.ATIVA ||
                toAccount.getStatus() != AccountStatus.ATIVA) {
            throw new IllegalArgumentException("Ambas as contas devem estar ativas.");
        }
        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }
        if (fromAccount.getSaldo().compareTo(dto.getValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        fromAccount.setSaldo(fromAccount.getSaldo().subtract(dto.getValor()));
        toAccount.setSaldo(toAccount.getSaldo().add(dto.getValor()));

        TransactionEntity debitTransaction = new TransactionEntity();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setCounterAccount(toAccount);
        debitTransaction.setTransactionType(TransactionType.TRANSFERENCIA);
        debitTransaction.setValue(dto.getValor().negate());
        debitTransaction.setStatus(TransactionStatus.COMPLETED);
        debitTransaction.setDescription(dto.getDescricao());
        debitTransaction.setCurrency("BRL");

        TransactionEntity creditTransaction = new TransactionEntity();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setCounterAccount(fromAccount);
        creditTransaction.setTransactionType(TransactionType.TRANSFERENCIA);
        creditTransaction.setValue(dto.getValor()); // positivo
        creditTransaction.setStatus(TransactionStatus.COMPLETED);
        creditTransaction.setDescription(dto.getDescricao());
        creditTransaction.setCurrency("BRL");

        transactionRepository.save(debitTransaction);
        TransactionEntity savedCredit = transactionRepository.save(creditTransaction);

        return TransactionMapper.toResponse(savedCredit);
    }


    public TransactionResponseDTO getTransactionById(Long transactionId) {
        TransactionEntity transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada."));
        return TransactionMapper.toResponse(transaction);
    }

    public List<TransactionResponseDTO> getStatement(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada."));
        List<TransactionEntity> transactions =
                transactionRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }
}
