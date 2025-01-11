package com.example.account_service.services;


import com.example.account_service.dto.AccountDTO;
import com.example.account_service.entity.Account;
import com.example.account_service.entity.AppUser;
import com.example.account_service.payload.CreateAccountRequest;
import com.example.account_service.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<AccountDTO> getAccountById(UUID id) {
        return accountRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public AccountDTO createAccount(CreateAccountRequest createAccountRequest) {
        Account account = Account.builder()
                .appUser(AppUser.builder().id(createAccountRequest.getUserId()).build())
                .accountNumber(createAccountRequest.getAccountNumber())
                .balance(createAccountRequest.getBalance())
                .build();
        Account savedAccount = accountRepository.save(account);
        return convertToDTO(savedAccount);
    }

    @Transactional
    @Override
    public void deleteAccount(UUID id) {
        accountRepository.softDeleteById(id);
    }

    private AccountDTO convertToDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .userId(account.getAppUser().getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .deletedAt(account.getDeletedAt())
                .build();
    }
}