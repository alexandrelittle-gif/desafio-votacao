package com.desafio.votacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
            .title("Desafio Votação API")
            .version("v1")
            .description("API para cadastro de pautas, abertura de sessões, votação e apuração"));
    }
}
