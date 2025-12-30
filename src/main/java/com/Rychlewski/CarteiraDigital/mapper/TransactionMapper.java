package com.Rychlewski.CarteiraDigital.mapper;

import com.Rychlewski.CarteiraDigital.dto.transaction.TransactionResponseDTO;
import com.Rychlewski.CarteiraDigital.entity.AccountEntity;
import com.Rychlewski.CarteiraDigital.entity.TransactionEntity;
import com.Rychlewski.CarteiraDigital.enums.TransactionStatus;
import com.Rychlewski.CarteiraDigital.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionMapper {

    public static TransactionEntity createTransaction(
            AccountEntity account,
            AccountEntity counterAccount,
            TransactionType type,
            BigDecimal valor,
            String descricao
    ) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setCounterAccount(counterAccount);
        transaction.setTransactionType(type);
        transaction.setValue(valor);
        transaction.setDescription(descricao);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCurrency("BRL");
        return transaction;
    }


    public static TransactionResponseDTO toResponse(TransactionEntity savedTransaction) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setId(savedTransaction.getId());
        response.setAccountId(savedTransaction.getAccount().getId());
        if (savedTransaction.getCounterAccount() != null) {
            response.setCounterAccountId(savedTransaction.getCounterAccount().getId());
        }
        response.setType(savedTransaction.getTransactionType());
        response.setStatus(savedTransaction.getStatus());
        response.setValor(savedTransaction.getValue());
        response.setDescricao(savedTransaction.getDescription());
        response.setCreatedAt(savedTransaction.getCreatedAt());
        response.setProcessedAt(savedTransaction.getProcessedAt());
        return response;
    }
}
