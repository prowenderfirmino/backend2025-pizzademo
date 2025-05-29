package com.senac.pizzademo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI para documentação automática dos endpoints REST.
 * 
 * Após iniciar o projeto, acesse a documentação interativa em:
 * http://localhost:8080/swagger-ui.html ou http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Pizza Demo API")
                .version("1.0")
                .description("API para gerenciamento de pizzas, ingredientes e cardápio com autenticação JWT."));
    }
}
