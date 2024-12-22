package com.example.finance_app.repositories;


import com.example.finance_app.entity.OrganizationSide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationSideRepository extends JpaRepository<OrganizationSide, UUID> {
}
