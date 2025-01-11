package com.example.transaction_service.configurations;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter for handling JWT authentication on incoming requests.
 */
@Component
public class SecurityContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Extract user details from headers
        String userId = request.getHeader("X-User-Id");
        String rolesHeader = request.getHeader("X-User-Roles");

        if (userId != null && rolesHeader != null) {
            // Convert roles to GrantedAuthority objects
            List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            // Create an Authentication object
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userId, // Principal (e.g., user ID or username)
                    null,   // Credentials (null since it's already authenticated)
                    authorities
            );

            // Set the Authentication object in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
