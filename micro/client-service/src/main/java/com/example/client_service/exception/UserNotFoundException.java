package com.example.client_service.exception;


import com.example.client_service.exception.config.ExceptionMessage;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(ExceptionMessage.USER_NOT_FOUND);
    }
}
