package com.example.ecom;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomServiceApplication.class, args);
    }

    @Bean
    public OpenAPI openAPI(@Value("${application.version}") String appVersion
            , @Value("${application.name}") String appName
            , @Value("${application.description}") String appDescription
            , @Value("${application.license}") String appLicense) {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic"))
                )
                .info(new Info()
                        .title(appName)
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name(appLicense))
                );
    }

}
