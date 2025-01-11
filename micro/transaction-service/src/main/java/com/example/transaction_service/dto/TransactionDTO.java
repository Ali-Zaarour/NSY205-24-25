package com.example.transaction_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("accountId")
    private UUID accountId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("type")
    private String type;

    @JsonProperty("createdAt")
    private Timestamp createdAt;
}