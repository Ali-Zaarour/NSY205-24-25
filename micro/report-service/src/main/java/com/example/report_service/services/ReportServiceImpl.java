package com.example.report_service.services;

import com.example.report_service.dto.ReportSummaryDTO;
import com.example.report_service.dto.UserReportSummaryDTO;
import com.example.report_service.entity.Account;
import com.example.report_service.repositories.AccountRepository;
import com.example.report_service.repositories.AppUserRepository;
import com.example.report_service.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    private final AppUserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ReportServiceImpl(AppUserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public ReportSummaryDTO getReportSummary() {
        int userCount = (int) userRepository.count();
        int accountCount = (int) accountRepository.count();
        double totalBalance = accountRepository.findAll().stream()
                .mapToDouble(Account::getBalance)
                .sum();
        int transactionCount = (int) transactionRepository.count();

        return new ReportSummaryDTO(userCount, accountCount, totalBalance, transactionCount);
    }

    @Override
    public UserReportSummaryDTO getUserReportSummaryByUserId(UUID userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        int accountCount = (int) accountRepository.countByAppUserId(user.getId());
        double totalBalance = accountRepository.findByAppUserId(user.getId()).stream()
                .mapToDouble(Account::getBalance)
                .sum();
        int transactionCount = accountRepository.findByAppUserId(user.getId()).stream()
                .mapToInt(account -> (int) transactionRepository.countByAccountId(account.getId()))
                .sum();

        return UserReportSummaryDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .userNumber(user.getId().hashCode())
                .accountNumber(accountCount)
                .totalBalance(totalBalance)
                .transactionNumber(transactionCount)
                .build();
    }
}
