package com.example.auth_service.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("${management.endpoints.web.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${management.endpoints.web.cors.allowed-methods}")
    private String[] allowedMethods;

    @Value("${management.endpoints.web.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${management.endpoints.web.cors.exposed-headers}")
    private String[] exposedHeaders;

    @Bean
    public CorsConfigurationSource apiConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList(allowedMethods));
        configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
        configuration.setExposedHeaders(Arrays.asList(exposedHeaders));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}