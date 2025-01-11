package com.example.report_service.repositories;


import com.example.report_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByAppUserId(UUID userId);
    long countByAppUserId(UUID userId);
}
