package br.edu.iff.ccc.caronaamiga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Carona Amiga API")
                .version("v1.0.0")
                .description("API RESTful para compartilhamento de caronas universitárias no IFF.")
                .contact(new Contact()
                    .name("Equipe Carona Amiga")
                    .email("suporte@caronaamiga.iff.edu.br"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://springdoc.org")));
    }
}