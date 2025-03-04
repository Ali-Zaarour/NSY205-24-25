package com.example.finance_app.controllers;


import com.example.finance_app.dto.AppUserDTO;
import com.example.finance_app.exception.config.ErrorStruct;
import com.example.finance_app.exception.config.ExceptionMessage;
import com.example.finance_app.payload.LoginRequest;
import com.example.finance_app.services.AuthenticationService;
import com.example.finance_app.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login api")
@RequestMapping("/unsecured/authentication")
public class LoginController {

    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(
            description = "This endpoint provide the login functionality",
            summary = "login",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppUserDTO.class)),
                            headers = @Header(
                                    name = "token",
                                    description = "jwt token",
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class)))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequest.class)))
    )
    @PostMapping
    public ResponseEntity<AppUserDTO> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest).map(data -> ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, (String) data.get(Constants.SECURITY_ATTRIBUTE_TOKEN))
                .contentType(MediaType.APPLICATION_JSON)
                .body((AppUserDTO) data.get(Constants.APP_USER_DTO))).orElseThrow(() -> new AccessDeniedException(ExceptionMessage.ACCESS_DENIED));
    }

}
