package com.example.finance_app.services;


import com.example.finance_app.payload.LoginRequest;

import java.util.Map;
import java.util.Optional;

public interface AuthenticationService {
    Optional<Map<String, Object>> login(LoginRequest loginRequest);
}
