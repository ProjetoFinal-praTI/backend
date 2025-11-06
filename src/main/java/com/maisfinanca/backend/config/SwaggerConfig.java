package com.maisfinanca.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MaisFinança API")
                        .version("1.0.0")
                        .description("📘 Documentação da API do sistema **MaisFinança** — gerencie usuários, finanças e autenticação JWT.")
                        .contact(new Contact()
                                .name("Equipe MaisFinança")
                                .email("suportemaisfinanca@gmail.com")
                                .url("https://maisfinanca.com.br")));
    }
}