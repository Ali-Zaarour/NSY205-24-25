package com.example.finance_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReportSummaryDTO {

    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("user_number")
    private int userNumber;
    @JsonProperty("account_number")
    private int accountNumber;
    @JsonProperty("total_balance")
    private double totalBalance;
    @JsonProperty("transaction_number")
    private int transactionNumber;
}
