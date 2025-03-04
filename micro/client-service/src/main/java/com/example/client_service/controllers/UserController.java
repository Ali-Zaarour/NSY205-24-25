package com.example.client_service.controllers;


import com.example.client_service.dto.AppUserDTO;
import com.example.client_service.dto.UpdatedAppUserInfoDTO;
import com.example.client_service.exception.config.ErrorStruct;
import com.example.client_service.exception.config.ExceptionMessage;
import com.example.client_service.payload.CreateUserRequest;
import com.example.client_service.payload.UpdateUserRequest;
import com.example.client_service.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "User api")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            description = "This endpoint is provided to create a new client",
            summary = "create new user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppUserDTO.class))),
                    @ApiResponse(
                            description = "Conflict",
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserRequest.class)))
    )
    @PostMapping
    @PreAuthorize("hasAuthority('permission:create-user')")
    public ResponseEntity<AppUserDTO> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        var createdUser = userService.createUser(createUserRequest);
        return createdUser.map(ResponseEntity::ok).orElseThrow(() -> new DataIntegrityViolationException(ExceptionMessage.DATA_INTEGRITY_VIOLATION));
    }

    @Operation(
            description = "This endpoint is provided to update either the username or the password or the organization of a user",
            summary = "update user",
            responses = {
                    @ApiResponse(
                            description = "Success", // no problem with the update
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppUserDTO.class))),
                    @ApiResponse(
                            description = "Conflict, update conflicts with the current state of the resource", // in case for example the username already exists
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
                    @ApiResponse(
                            description = "Unauthorized, the user does not have the required permission to perform the action", // in case the user does not have the required permission
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
                    @ApiResponse(
                            description = "Bad Request, the request is not valid", // in case the request is not valid, nothing to update
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
                    @ApiResponse(
                            description = "Not Found, the user with the provided id does not exist", // in case the user does not exist
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorStruct.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUserRequest.class)))
    )
    @PatchMapping
    @PreAuthorize("hasAuthority('permission:update-user')")
    public ResponseEntity<UpdatedAppUserInfoDTO> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, Authentication authentication) {
        var updatedUser = userService.updateUser(updateUserRequest,  UUID.fromString((String) authentication.getPrincipal()));
        return updatedUser.map(ResponseEntity::ok).orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOTHING_TO_UPDATE));
    }
}
