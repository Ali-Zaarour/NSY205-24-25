package com.example.finance_app.repositories;

import com.example.finance_app.entity.AppUserPermissionMapping;
import com.example.finance_app.entity.AppUserPermissionMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppUserPermissionMappingRepository extends JpaRepository<AppUserPermissionMapping, AppUserPermissionMappingId> {
    List<AppUserPermissionMapping> findByIdAppUserIdAndAppUserPermissionIdIn(UUID appUserId, List<UUID> appUserPermissionIds);
}
