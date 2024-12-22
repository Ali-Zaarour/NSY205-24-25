package com.example.finance_app.controllers;


import com.example.finance_app.dto.AppUserDTO;
import com.example.finance_app.dto.UpdatedAppUserInfoDTO;
import com.example.finance_app.entity.AppUser;
import com.example.finance_app.exception.config.ErrorStruct;
import com.example.finance_app.exception.config.ExceptionMessage;
import com.example.finance_app.payload.CreateUserRequest;
import com.example.finance_app.payload.UpdateUserRequest;
import com.example.finance_app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User api")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/admin/users")
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
    public ResponseEntity<AppUserDTO> createUser(@Valid @RequestBody CreateUserRequest createUserRequest, @AuthenticationPrincipal @NonNull AppUser principalDetails) {
        var createdUser = userService.createUser(createUserRequest, principalDetails.getId());
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
    public ResponseEntity<UpdatedAppUserInfoDTO> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @AuthenticationPrincipal @NonNull AppUser principalDetails) {
        var updatedUser = userService.updateUser(updateUserRequest, principalDetails.getId());
        return updatedUser.map(ResponseEntity::ok).orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOTHING_TO_UPDATE));
    }
}
