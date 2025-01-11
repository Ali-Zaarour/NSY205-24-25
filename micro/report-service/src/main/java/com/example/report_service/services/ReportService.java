package com.example.report_service.services;

import com.example.report_service.dto.ReportSummaryDTO;
import com.example.report_service.dto.UserReportSummaryDTO;

import java.util.UUID;

public interface ReportService {

    ReportSummaryDTO getReportSummary();

    UserReportSummaryDTO getUserReportSummaryByUserId(UUID userId);

}
