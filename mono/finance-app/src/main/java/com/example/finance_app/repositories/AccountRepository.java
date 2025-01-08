package com.example.finance_app.repositories;

import com.example.finance_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByAppUserId(UUID userId);

    long countByAppUserId(UUID userId);

    @Modifying
    @Query("update Account a set a.deletedAt = current_timestamp where a.id = :id")
    void softDeleteById(@Param("id") UUID id);

}
