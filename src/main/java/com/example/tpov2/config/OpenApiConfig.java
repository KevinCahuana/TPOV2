package com.example.tpov2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TPO V2 - API de Algoritmos sobre una Red Social de Usuarios")
                        .version("1.0.0")
                        .description("API REST para demostrar la implementación de diversos algoritmos (búsqueda, ordenamiento, etc.) sobre una red social de usuarios modelada con Neo4j.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("tu.email@ejemplo.com")
                                .url("https://github.com/tu-usuario/TPOV2"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
