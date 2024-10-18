package com.example.contactlistmanager.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Contact List Manager API")
                        .version("1.0")
                        .description("API documentation for the Contact List Manager project")
                        .contact(new Contact()
                                .name("Amruta Patil")
                                .email("amrutaapatil99@gmail.com")
                                .url("https://your-website.com"))
                );
    }
}
