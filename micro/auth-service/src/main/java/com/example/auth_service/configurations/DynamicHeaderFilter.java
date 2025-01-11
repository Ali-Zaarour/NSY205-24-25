package com.example.auth_service.configurations;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@Component
public class DynamicHeaderFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest servletRequest, jakarta.servlet.ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Wrap the request to cache its body (if needed)
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);

        // Read attributes set by JWTFilter
        String userId = (String) cachingRequest.getAttribute("X-User-Id");
        String userRoles = (String) cachingRequest.getAttribute("X-User-Roles");

        // Wrap the request to add headers
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(cachingRequest) {
            @Override
            public String getHeader(String name) {
                if ("X-User-Id".equalsIgnoreCase(name)) {
                    return userId;
                }
                if ("X-User-Roles".equalsIgnoreCase(name)) {
                    return userRoles;
                }
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                // Combine existing headers with new ones
                Set<String> headerNames = new HashSet<>(Collections.list(super.getHeaderNames()));
                if (userId != null) {
                    headerNames.add("X-User-Id");
                }
                if (userRoles != null) {
                    headerNames.add("X-User-Roles");
                }
                return Collections.enumeration(headerNames);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if ("X-User-Id".equalsIgnoreCase(name)) {
                    return Collections.enumeration(Collections.singleton(userId));
                }
                if ("X-User-Roles".equalsIgnoreCase(name)) {
                    return Collections.enumeration(Collections.singleton(userRoles));
                }
                return super.getHeaders(name);
            }
        };

        // Continue with the filter chain using the wrapped request
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
