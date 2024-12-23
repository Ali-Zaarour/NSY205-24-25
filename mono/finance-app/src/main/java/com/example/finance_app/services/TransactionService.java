package com.example.finance_app.services;

import com.example.finance_app.dto.TransactionDTO;
import com.example.finance_app.payload.CreateTransactionRequest;

import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO createTransaction(CreateTransactionRequest createTransactionRequest);

    Optional<TransactionDTO> getTransactionById(UUID id);

    void deleteTransaction(UUID id);
}
