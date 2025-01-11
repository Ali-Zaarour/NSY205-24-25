package com.example.auth_service.configurations;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Log HTTP method and URL
        logger.info("Incoming Request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURL());

        // Log headers
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header: {} = {}", headerName, httpRequest.getHeader(headerName));
        }

        // Log query parameters
        httpRequest.getParameterMap().forEach((key, value) -> {
            logger.info("Query Param: {} = {}", key, String.join(", ", value));
        });

        // Proceed with the request
        chain.doFilter(request, response);
    }
}
