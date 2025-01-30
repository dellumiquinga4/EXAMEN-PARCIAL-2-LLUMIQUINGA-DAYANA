package com.banquito.branch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@OpenAPIDefinition(
    info = @Info(
        title = "Branch Service API",
        version = "1.0",
        description = "API for managing bank branches and their holidays",
        contact = @Contact(
            name = "Banquito Support",
            email = "support@banquito.com",
            url = "https://banquito.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Development Server"
        )
    }
)
public class BranchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BranchServiceApplication.class, args);
    }
} 