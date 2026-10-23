package com.biblio.Projet_fil_rouge.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblio.Projet_fil_rouge.dto.AuteurRequest;
import com.biblio.Projet_fil_rouge.dto.AuteurResponse;
import com.biblio.Projet_fil_rouge.mapper.AuteurMapper;
import com.biblio.Projet_fil_rouge.model.Auteur;
import com.biblio.Projet_fil_rouge.service.AuteurService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/v1")
public class AuteurController {
    private final AuteurService auteurService;

    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }

    @GetMapping ("/auteurs")
    public ResponseEntity<PagedModel<AuteurResponse>> touteLaListe(
        @PageableDefault (size = 10, sort = "nom") Pageable pageable) {
        Page<Auteur> auteurs = auteurService.recupererTous(pageable);
        List<AuteurResponse> contenu = AuteurMapper.versResponses(auteurs.getContent());
        Page<AuteurResponse> auteurDto = new PageImpl<>(contenu, auteurs.getPageable(), auteurs.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(auteurDto));
    }

    @GetMapping ("/auteurs/{id}")
    public ResponseEntity<AuteurResponse> trouverAuteur(@PathVariable Long id) {
        return auteurService.recupererParId(id)
        .map(AuteurMapper::versResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping ("/auteurs")
    public ResponseEntity<AuteurResponse> creerAuteur(@Valid @RequestBody AuteurRequest auteurRequest) {
        Auteur auteur = AuteurMapper.versEntite(auteurRequest);
        Auteur nouvelAuteur = auteurService.creerAuteur(auteur);
        AuteurResponse auteurResponse = AuteurMapper.versResponse(nouvelAuteur);
        return ResponseEntity.ok().body(auteurResponse);
    }

    @PutMapping ("/auteurs/{id}")
    public ResponseEntity<AuteurResponse> changerAuteur(@PathVariable Long id, @RequestBody Auteur auteur) {
        return auteurService.updateAuteur(id, auteur)
        .map(AuteurMapper::versResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping ("/auteurs/{id}") 
    public ResponseEntity<Void> supprimerAuteur(@PathVariable Long id) {
        if (auteurService.supprimerAuteur(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    
}
