package com.example.auth_service.configurations.customHandler;


import com.example.auth_service.exception.config.ErrorStruct;
import com.example.auth_service.exception.config.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorStruct errorStruct = ErrorStruct.withDefaultLocalDateTime(HttpStatus.UNAUTHORIZED, ExceptionMessage.UNAUTHORIZED_ACTION).build();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorStruct));
    }
}
