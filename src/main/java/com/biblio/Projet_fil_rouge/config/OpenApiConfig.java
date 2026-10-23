package com.biblio.Projet_fil_rouge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
 @Bean
 public OpenAPI customOpenAPI() {
 return new OpenAPI()
 .info(new Info()
 .title("API REST Produits")
 .version("1.0.0")
 .description("API de gestion d'une bibliothèque — Formation SpringBoot")
 .contact(new Contact()
 .name("Équipe Maxime Petit, Théo Rigagneau, Thomas Pisanne, Jérémy Girard")
.email("bibliotheque@exemple.fr")));
 }
}
