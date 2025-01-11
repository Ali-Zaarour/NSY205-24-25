package com.example.auth_service.utils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.auth_service.repositories.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Filter for handling JWT authentication on incoming requests.
 */

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final AppUserRepository appUserRepository;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService, AppUserRepository appUserRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Extracting the "token" header
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Optional.ofNullable(requestTokenHeader).isEmpty() || !requestTokenHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Verify token and extract username
            var jwtUsername = jwtUtil.validateJWTTokenAndRetrieveSubject(requestTokenHeader.substring(7));

            //verifies that userInfo is not empty, and not authenticate yet
            if (jwtUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //get user details if exist
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtUsername);
                if (Optional.ofNullable(userDetails).isPresent()) {
                    //create auth token
                    var usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null
                                    , userDetails.getAuthorities());
                    //populated with web-related details extracted from the incoming HTTP request
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    // Setting the authentication on the Security Context using the created token
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    // Extract roles or permissions from the UserDetails object
                    String roles = userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .reduce((a, b) -> a + "," + b) // Join roles with a comma
                            .orElse("");

                    var entity = appUserRepository.findByUsername(jwtUsername).get();

                    // Add user details to the response headers for downstream services
                    request.setAttribute("X-User-Id", entity.getId().toString());
                    request.setAttribute("X-User-Roles", roles);
                }
            }
        } catch (JWTVerificationException exc) {
            // Failed to verify JWT
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Token");
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response);
    }
}