package br.com.flavio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("RESTFull API with Spring Boot and Java 18")
                        .version("v1")
                        .description("API to knowlegde demonstration")
                        .termsOfService("htto://org.spring")
                        .license(new License().name("Apache 2.0")
                                .url("to be define")));
    }
}
