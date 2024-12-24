package com.example.finance_app.services;

import com.example.finance_app.dto.ReportSummaryDTO;
import com.example.finance_app.dto.UserReportSummaryDTO;

import java.util.UUID;

public interface ReportService {

    ReportSummaryDTO getReportSummary();

    UserReportSummaryDTO getUserReportSummaryByUserId(UUID userId);

}
