package com.biblio.Projet_fil_rouge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * Configuration de la documentation OpenAPI (Swagger UI).
 * Ne modifie pas le comportement de l'API : seulement les informations affichées.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Équipe Formation");
        contact.setEmail("formation@exemple.fr");

        Info info = new Info();
        info.setTitle("API Bibliothèque");
        info.setVersion("1.0.0");
        info.setDescription("Projet fil rouge — gestion des auteurs, des livres et des emprunts.");
        info.setContact(contact);

        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        return openAPI;
    }
}
