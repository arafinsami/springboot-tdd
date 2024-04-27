package com.sami.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Sami",
                        email = "sami.cse.1112@gmail.com",
                        url = "https://www.linkedin.com/in/samiul-arafin/"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - Sami",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://www.linkedin.com/in/samiul-arafin/"
                )
        }
)
public class OpenApiConfig {
}
