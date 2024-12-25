package com.example.finance_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportSummaryDTO {
    private int userNumber;
    private int accountNumber;
    private double totalBalance;
    private int transactionNumber;
}