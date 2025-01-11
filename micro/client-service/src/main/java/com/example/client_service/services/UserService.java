package com.example.client_service.services;


import com.example.client_service.dto.AppUserDTO;
import com.example.client_service.dto.UpdatedAppUserInfoDTO;
import com.example.client_service.payload.CreateUserRequest;
import com.example.client_service.payload.UpdateUserRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<AppUserDTO> createUser(CreateUserRequest createUserRequest);
    Optional<UpdatedAppUserInfoDTO> updateUser(UpdateUserRequest updateUserRequest, UUID principalId);
}
