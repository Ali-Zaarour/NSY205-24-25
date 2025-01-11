package com.example.transaction_service.services;

import com.example.transaction_service.dto.TransactionDTO;
import com.example.transaction_service.entity.Account;
import com.example.transaction_service.entity.Status;
import com.example.transaction_service.entity.Transaction;
import com.example.transaction_service.entity.TransactionType;
import com.example.transaction_service.payload.CreateTransactionRequest;
import com.example.transaction_service.repositories.TransactionRepository;
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
                .status(Status.builder().id(createTransactionRequest.getStatusId()).build())
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
