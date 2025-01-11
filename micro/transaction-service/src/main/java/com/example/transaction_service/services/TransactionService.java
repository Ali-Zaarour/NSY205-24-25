package com.example.transaction_service.services;

import com.example.transaction_service.dto.TransactionDTO;
import com.example.transaction_service.payload.CreateTransactionRequest;

import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO createTransaction(CreateTransactionRequest createTransactionRequest);

    Optional<TransactionDTO> getTransactionById(UUID id);

    void deleteTransaction(UUID id);
}
