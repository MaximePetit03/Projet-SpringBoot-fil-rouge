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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.biblio.Projet_fil_rouge.dto.AuteurRequest;
import com.biblio.Projet_fil_rouge.dto.AuteurResponse;
import com.biblio.Projet_fil_rouge.dto.LivreResponse;
import com.biblio.Projet_fil_rouge.mapper.AuteurMapper;
import com.biblio.Projet_fil_rouge.mapper.LivreMapper;
import com.biblio.Projet_fil_rouge.model.Auteur;
import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.service.AuteurService;
import com.biblio.Projet_fil_rouge.service.LivreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/v1")
@Tag (name = "Auteurs", description = "Gestion des auteurs de la bibliothèque")
public class AuteurController {
    private final AuteurService auteurService;
    private final LivreService livreService;

    public AuteurController(AuteurService auteurService, LivreService livreService) {
        this.auteurService = auteurService;
        this.livreService = livreService;
    }

    @Operation (
        summary = "Lister tous les auteurs"
    )
    @ApiResponse (responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping ("/auteurs")
    public ResponseEntity<PagedModel<AuteurResponse>> trouverAuteurParNom(
        @RequestParam (required = false) String nom,
        @PageableDefault (size = 10, sort = "nom") Pageable pageable
    ) {
        Page<Auteur> auteurs = auteurService.trouverAuteurs(nom, pageable);
        List<AuteurResponse> contenu = AuteurMapper.versResponses(auteurs.getContent());
        Page<AuteurResponse> auteurDto = new PageImpl<>(contenu, auteurs.getPageable(), auteurs.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(auteurDto));
    }

    @Operation (
        summary = "Recherche un auteur"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "L'auteur est retourné avec succès"),
        @ApiResponse (responseCode = "404", description = "L'auteur n'existe pas"),
    })
    @GetMapping ("/auteurs/{id}")
    public ResponseEntity<AuteurResponse> trouverAuteur(@PathVariable Long id) {
        return auteurService.recupererParId(id)
        .map(AuteurMapper::versResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }


    @Operation (
        summary = "Rechercher les livres d'un auteur"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Les livres de l'auteur sont retournés avec succès"),
        @ApiResponse (responseCode = "404", description = "L'auteur n'existe pas"),
    })
    @GetMapping ("/auteurs/{id}/livres")
    public ResponseEntity<PagedModel<LivreResponse>> trouverLivresAuteur(
        @PathVariable Long id,
        @RequestParam (required = false) String nom,
        @PageableDefault (size = 10, sort = "titre") Pageable pageable
    ) {
        Page<Livre> livresAuteur =  livreService.listerLivres(id, pageable);
        List<LivreResponse> contenu = LivreMapper.versResponses(livresAuteur.getContent());
        Page<LivreResponse> livreDto = new PageImpl<>(contenu, livresAuteur.getPageable(), livresAuteur.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(livreDto));
    }

    @Operation (
        summary = "Creer un auteur"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "201", description = "Les livres de l'auteur sont retournés avec succès"),
        @ApiResponse (responseCode = "400", description = "La requête n'est pas bonne (BAD REQUEST)"),
    })
    @PostMapping ("/auteurs")
    public ResponseEntity<AuteurResponse> creerAuteur(@Valid @RequestBody AuteurRequest auteurRequest) {
        Auteur auteur = AuteurMapper.versEntite(auteurRequest);
        Auteur nouvelAuteur = auteurService.creerAuteur(auteur);
        AuteurResponse auteurResponse = AuteurMapper.versResponse(nouvelAuteur);
        return ResponseEntity.status(HttpStatus.CREATED).body(auteurResponse);
    }

    @Operation (
        summary = "Changer les informations d'un auteur"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Les informations de l'auteur sont retournés avec succès"),
        @ApiResponse (responseCode = "404", description = "L'auteur n'existe pas"),
        @ApiResponse (responseCode = "400", description = "Les informations dans le corps sont mauvaises (BAD REQUEST)")
    })
    @PutMapping ("/auteurs/{id}")
    public ResponseEntity<AuteurResponse> changerAuteur(@Valid @PathVariable Long id, @RequestBody AuteurRequest auteurRequest) {
        Auteur auteur = AuteurMapper.versEntite(auteurRequest);
        return auteurService.updateAuteur(id, auteur)
        .map(AuteurMapper::versResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }


    @Operation (
        summary = "Supprimer un auteur"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "204", description = "L'auteur a été supprimé avec succès"),
        @ApiResponse (responseCode = "404", description = "L'auteur n'existe pas"),
    })
    @DeleteMapping ("/auteurs/{id}") 
    public ResponseEntity<Void> supprimerAuteur(@PathVariable Long id) {
        if (auteurService.supprimerAuteur(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
