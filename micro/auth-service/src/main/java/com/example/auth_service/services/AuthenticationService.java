package com.example.auth_service.services;


import com.example.auth_service.payload.LoginRequest;

import java.util.Map;
import java.util.Optional;

public interface AuthenticationService {
    Optional<Map<String, Object>> login(LoginRequest loginRequest);
}
