package com.example.transaction_service.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "client-service",
                        email = "example@outlook.com",
                        url = "https://example-url"),
                description = "OpenApi documentation",
                title = "OpenApi specification",
                version = "1.0"
        ),
        servers = {
                @Server(description = "Local Env", url = "http://localhost:8092/micro/report-service"),
                @Server(description = "Prod Env", url = "https://prod-ex.com")
        }
        //if all endpoints are secure, 100% add this as global config
        //else set annotation on every secure controller
        //security = @SecurityRequirement(name = "bearerAuth")
)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT Auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class Swagger {
}
