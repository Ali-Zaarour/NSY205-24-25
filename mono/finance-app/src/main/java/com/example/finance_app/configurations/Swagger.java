package com.example.finance_app.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "finance-app",
                        email = "example@outlook.com",
                        url = "https://example-url"),
                description = "OpenApi documentation for university API",
                title = "OpenApi specification activity management system",
                version = "1.0"
        ),
        servers = {
                @Server(description = "Local Env", url = "http://localhost:7500/mono/finance-app"),
                @Server(description = "Prod Env", url = "https://prod-ex.com")
        }
        //if all endpoints are secure, 100% add this as global config
        //else set annotation on every secure controller
        //security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class Swagger {
}
