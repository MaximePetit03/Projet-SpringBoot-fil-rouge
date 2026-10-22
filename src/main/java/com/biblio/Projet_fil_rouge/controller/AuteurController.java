package com.biblio.Projet_fil_rouge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblio.Projet_fil_rouge.service.AuteurService;

@RestController
@RequestMapping ("/api/v1")
public class AuteurController {
    private final AuteurService auteurService;

    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }
    
}
