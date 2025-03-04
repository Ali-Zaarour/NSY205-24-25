package com.example.auth_service.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NonNull
    @NotEmpty(message = "username.is.required")
    @Size(min = 2, max =200, message = "username.must.be.between.5.and.200.characters")
    private String username;

    @NonNull
    @NotEmpty(message = "password.is.required")
    @Size(min = 10, message = "password.must.be.at.least.10.characters")
    private String password;
}
