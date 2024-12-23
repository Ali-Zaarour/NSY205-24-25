package com.example.finance_app.services;

import com.example.finance_app.dto.TransactionDTO;
import com.example.finance_app.entity.Account;
import com.example.finance_app.entity.Transaction;
import com.example.finance_app.entity.TransactionType;
import com.example.finance_app.payload.CreateTransactionRequest;
import com.example.finance_app.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Override
    public TransactionDTO createTransaction(CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = Transaction.builder()
                .account(Account.builder().id(createTransactionRequest.getAccountId()).build())
                .amount(createTransactionRequest.getAmount())
                .transactionType(TransactionType.builder().id(createTransactionRequest.getTypeId()).build())
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }

    public Optional<TransactionDTO> getTransactionById(UUID id) {
        return transactionRepository.findById(id).map(this::convertToDTO);
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.deleteById(id);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .amount(transaction.getAmount())
                .type(transaction.getTransactionType().getKey())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
