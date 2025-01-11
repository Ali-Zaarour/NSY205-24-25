package com.example.transaction_service.controllers;

import com.example.transaction_service.dto.TransactionDTO;
import com.example.transaction_service.payload.CreateTransactionRequest;
import com.example.transaction_service.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Tag(name = "Transaction api")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create a new transaction")
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionRequest createTransactionRequest) {
        TransactionDTO createdTransaction = transactionService.createTransaction(createTransactionRequest);
        return ResponseEntity.ok(createdTransaction);
    }

    @Operation(summary = "Get a transaction by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable UUID id) {
        Optional<TransactionDTO> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a transaction by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}