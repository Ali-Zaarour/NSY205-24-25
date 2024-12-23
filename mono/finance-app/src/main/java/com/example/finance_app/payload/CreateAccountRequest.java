package com.example.finance_app.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest {

    @NotNull
    @JsonProperty("userId")
    private UUID userId;

    @NotNull
    @JsonProperty("accountNumber")
    private String accountNumber;

    @NotNull
    @JsonProperty("balance")
    private BigDecimal balance;
}