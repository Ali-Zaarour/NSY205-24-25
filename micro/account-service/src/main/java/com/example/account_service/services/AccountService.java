package com.example.account_service.services;


import com.example.account_service.dto.AccountDTO;
import com.example.account_service.payload.CreateAccountRequest;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Optional<AccountDTO> getAccountById(UUID id);

    AccountDTO createAccount(CreateAccountRequest createAccountRequest);

    void deleteAccount(UUID id);

}
