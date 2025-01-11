package com.example.client_service.repositories;


import com.example.client_service.entity.OrganizationSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationSideRepository extends JpaRepository<OrganizationSide, UUID> {
}
