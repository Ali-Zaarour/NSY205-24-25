package com.example.client_service.exception;


import com.example.client_service.exception.config.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UnauthorizedException() {
        super(ExceptionMessage.UNAUTHORIZED_ACTION);
    }

    public UnauthorizedException(String msg) {
        super(ExceptionMessage.UNAUTHORIZED_ACTION + " " + msg);
    }
}
