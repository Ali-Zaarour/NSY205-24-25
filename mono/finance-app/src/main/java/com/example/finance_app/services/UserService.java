package com.example.finance_app.services;



import com.example.finance_app.dto.AppUserDTO;
import com.example.finance_app.dto.UpdatedAppUserInfoDTO;
import com.example.finance_app.payload.CreateUserRequest;
import com.example.finance_app.payload.UpdateUserRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<AppUserDTO> createUser(CreateUserRequest createUserRequest);
    Optional<UpdatedAppUserInfoDTO> updateUser(UpdateUserRequest updateUserRequest, UUID principalId);
}
