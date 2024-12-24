package com.example.finance_app.controllers;

import com.example.finance_app.dto.AppUserDTO;
import com.example.finance_app.payload.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class LoginControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAuthenticateSuccess() {
        // Define the request payload
        LoginRequest loginRequest = new LoginRequest("m0_admin", "0123456789");

        // Perform the POST request
        ResponseEntity<AppUserDTO> response = restTemplate.postForEntity(
                "/mono/finance-app/unsecured/authentication",
                loginRequest,
                AppUserDTO.class
        );

        // Validate the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get(HttpHeaders.AUTHORIZATION));
        assertEquals("m0_admin", response.getBody().getUsername());
    }
}
