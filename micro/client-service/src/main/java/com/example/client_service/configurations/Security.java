package com.example.client_service.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>For more information about this topic, visit spring documentation.</p>
 * <p>
 * 1. <a href="https://docs.spring.io/spring-security/reference/index.html">Spring security</a>
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {

    private static final String[] WHITE_LIST_URL = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui",
            "/swagger-ui/**",
            "/actuator/**"
    };
    private final SecurityContextFilter securityContextFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public Security(AuthenticationProvider authenticationProvider, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, SecurityContextFilter securityContextFilter) {
        this.authenticationProvider = authenticationProvider;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.securityContextFilter = securityContextFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider)
                .addFilterBefore(securityContextFilter, UsernamePasswordAuthenticationFilter.class)
                // Rejecting request as unauthorized when entry point is reached
                .exceptionHandling(exHd -> exHd.authenticationEntryPoint(customAuthenticationEntryPoint));
        return httpSecurity.build();
    }
}
