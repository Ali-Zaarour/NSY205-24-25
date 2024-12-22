package com.example.finance_app.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class UpdateUserStatusRequest {

    @NonNull
    @NotEmpty(message = "user.id.is.required")
    private UUID userId;

    @NonNull
    @NotEmpty(message = "status.is.required")
    private UUID status;
}
