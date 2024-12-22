package com.example.finance_app.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NonNull
    @NotEmpty(message = "username.is.required")
    @Size(min = 2, max =20, message = "username.must.be.between.2.and.20.characters")
    private String username;

    @NonNull
    @NotEmpty(message = "password.is.required")
    @Size(min = 10, message = "password.must.be.at.least.10.characters")
    private String password;

    @NotNull
    private UUID organizationId;

}
