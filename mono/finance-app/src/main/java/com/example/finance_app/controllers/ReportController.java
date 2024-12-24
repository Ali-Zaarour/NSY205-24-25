package com.example.finance_app.controllers;

import com.example.finance_app.dto.ReportSummaryDTO;
import com.example.finance_app.dto.UserReportSummaryDTO;
import com.example.finance_app.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "Report api")
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Get report summary")
    @GetMapping("/summary")
    public ResponseEntity<ReportSummaryDTO> getReportSummary() {
        ReportSummaryDTO reportSummary = reportService.getReportSummary();
        return ResponseEntity.ok(reportSummary);
    }

    @Operation(summary = "Get user report summary by user ID")
    @GetMapping("/user-summary/{userId}")
    public ResponseEntity<UserReportSummaryDTO> getUserReportSummaryByUserId(@PathVariable UUID userId) {
        UserReportSummaryDTO userReportSummary = reportService.getUserReportSummaryByUserId(userId);
        return ResponseEntity.ok(userReportSummary);
    }
}
