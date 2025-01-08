package com.example.finance_app.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CreateTransactionRequest {

    @JsonProperty("accountId")
    private UUID accountId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("status_id")
    private UUID statusId;

    @JsonProperty("type_id")
    private UUID typeId;
}