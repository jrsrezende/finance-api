package br.com.jrsr.financeapi.infrastructure.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI agendaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Finance API")
                        .version("1.0.0")
                        .description("API for finance management")
                        .contact(new Contact()
                                .name("João Rezende")
                                .email("joaoricardorezende@gmail.com")
                        ))
                .components(new Components().addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .externalDocs(new ExternalDocumentation()
                        .description("Complete Documentation")
                        .url("https://github.com/jrsrezende/finance-api"));
    }
}
