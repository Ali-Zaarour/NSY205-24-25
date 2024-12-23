package com.example.finance_app.services;

import com.example.finance_app.dto.AccountDTO;
import com.example.finance_app.payload.CreateAccountRequest;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Optional<AccountDTO> getAccountById(UUID id);

    AccountDTO createAccount(CreateAccountRequest createAccountRequest);

    void deleteAccount(UUID id);

}
