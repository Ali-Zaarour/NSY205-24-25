package com.example.finance_app.services;

import com.example.finance_app.exception.config.ExceptionMessage;
import com.example.finance_app.mapper.AppUserAuthDetailsMapper;
import com.example.finance_app.payload.LoginRequest;
import com.example.finance_app.repositories.AppUserRepository;
import com.example.finance_app.utils.Constants;
import com.example.finance_app.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(AppUserRepository appUserRepository, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Optional<Map<String, Object>> login(LoginRequest loginRequest) {
        // validate the provided user detail
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // retrieve client meta-date
        var appUser = appUserRepository.findByUsername(loginRequest.getUsername());
        if (appUser.isPresent()) {
            if (!appUser.get().isEnabled()) throw new DisabledException(ExceptionMessage.USER_DISABLED);
            //generate token
            String token = jwtUtil.generateJWTToken(appUser.get());
            //create return value
            return Optional.of(new HashMap<String, Object>() {{
                put(Constants.APP_USER_DTO, AppUserAuthDetailsMapper.MAPPER.toAppUserDTO(appUser.get()));
                put(Constants.SECURITY_ATTRIBUTE_TOKEN, token);
            }});
        }
        return Optional.empty();
    }
}
