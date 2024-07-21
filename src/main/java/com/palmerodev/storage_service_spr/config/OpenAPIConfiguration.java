package com.palmerodev.storage_service_spr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        var server = new Server();
        server.setDescription("Fake Storage API Server. A simple API to manage categories, products and files.");

        var myContact = new Contact();
        myContact.setName("Victor Palmero Valdes");
        myContact.setEmail("palmerodev@gmail.com");
        myContact.setUrl("https://github.com/palmerovicdev");

        var information = new Info()
                .title("Fake Storage API")
                .version("1.0")
                .license(new io.swagger.v3.oas.models.info.License().name("MIT").url("https://opensource.org/licenses/MIT"))
                .description("This API exposes endpoints to manage categories, products and files.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }

}