package com.example.client_service.repositories;


import com.example.client_service.entity.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID>, AppUserRepositoryCustom {

    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}

@Repository
interface AppUserRepositoryCustom {
    int updateMainInfo(UUID userId, Map<String, Object> updates);
}

@Repository
class AppUserRepositoryCustomImpl implements AppUserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final Logger LOGGER = Logger.getLogger(AppUserRepositoryCustomImpl.class.getName());


    @Override
    @Transactional
    public int updateMainInfo(UUID userId, Map<String, Object> updates) {
        try {
            // Construct the dynamic update query
            StringBuilder queryString = new StringBuilder("UPDATE AppUser u SET ");
            updates.forEach((field, value) -> queryString.append("u.").append(field).append(" = :").append(field).append(", "));
            queryString.delete(queryString.length() - 2, queryString.length()); // Remove last comma
            queryString.append(" WHERE u.id = :id");

            // Create and set parameters for the query
            Query query = entityManager.createQuery(queryString.toString());
            updates.forEach(query::setParameter);
            query.setParameter("id", userId);

            // Execute the update query
            return query.executeUpdate();
        } catch (PersistenceException e) {
            // Handle persistence exceptions
            LOGGER.log(Level.SEVERE, "Error executing update query", e);
            throw e; // Rethrow the exception or handle it as needed
        } catch (IllegalArgumentException e) {
            // Handle illegal argument exceptions
            LOGGER.log(Level.SEVERE, "Invalid argument provided for update query", e);
            throw e; // Rethrow the exception or handle it as needed
        }
    }
}