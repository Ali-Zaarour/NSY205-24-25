package com.example.account_service.repositories;


import com.example.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Modifying
    @Query("update Account a set a.deletedAt = current_timestamp where a.id = :id")
    void softDeleteById(@Param("id") UUID id);

}
