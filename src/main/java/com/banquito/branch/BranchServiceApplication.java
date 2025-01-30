package com.banquito.branch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
@OpenAPIDefinition(
    info = @Info(
        title = "Branch Service API",
        version = "1.0",
        description = "API for managing bank branches and their holidays"
    )
)
public class BranchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BranchServiceApplication.class, args);
    }
} 