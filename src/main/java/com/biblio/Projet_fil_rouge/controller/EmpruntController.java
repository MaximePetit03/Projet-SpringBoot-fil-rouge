package com.biblio.Projet_fil_rouge.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblio.Projet_fil_rouge.dto.EmpruntRequest;
import com.biblio.Projet_fil_rouge.dto.EmpruntResponse;
import com.biblio.Projet_fil_rouge.mapper.EmpruntMapper;
import com.biblio.Projet_fil_rouge.model.Emprunt;
import com.biblio.Projet_fil_rouge.service.EmpruntService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Contrôleur REST pour les emprunts
 * Entrées : EmpruntRequest. Sorties : EmpruntResponse.
 */
@RestController
@RequestMapping("/api/v1/emprunts")
@Tag (name = "Emprunts", description = "Gestion des emprunts de livres de la bibliothèque")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @Operation (
        summary = "Lister tous les emprunts"
    )
    @ApiResponse (responseCode = "200", description = "Liste des emprunts retournée avec succès")
    // GET /api/v1/emprunts — Lister (paginé)
    @GetMapping
    public ResponseEntity<PagedModel<EmpruntResponse>> listerTous(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<Emprunt> page = empruntService.recupererTous(pageable);
        List<EmpruntResponse> contenu = EmpruntMapper.versResponses(page.getContent());
        Page<EmpruntResponse> empruntDto = new PageImpl<>(contenu, page.getPageable(), page.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(empruntDto));
        
    }

    // GET /api/v1/emprunts/{id} — Obtenir un emprunt
    @Operation (
        summary = "Recherche un emprunt"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "L'emprunt est retourné avec succès"),
        @ApiResponse (responseCode = "404", description = "L'emprunt n'existe pas"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpruntResponse> obtenirParId(@PathVariable Long id) {
        Optional<Emprunt> resultat = empruntService.recupererParId(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();                       // 404
        }
        return ResponseEntity.ok(EmpruntMapper.versResponse(resultat.get())); // 200
    }


    @Operation (
        summary = "Creer un emprunt"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "201", description = "L'emprunt a été crée avec succès"),
        @ApiResponse (responseCode = "400", description = "La requête n'est pas bonne (BAD REQUEST)"),
    })
    // POST /api/v1/emprunts — Créer un emprunt
    @PostMapping
    public ResponseEntity<EmpruntResponse> creer(@Valid @RequestBody EmpruntRequest request) {
        Emprunt empruntCree = empruntService.creer(EmpruntMapper.versEntite(request));
        URI emplacement = URI.create("/api/v1/emprunts/" + empruntCree.getId());
        return ResponseEntity
                .created(emplacement)                                       // 201 + Location
                .body(EmpruntMapper.versResponse(empruntCree));
    }


    @Operation (
        summary = "Changer une information d'un emprunt"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "L'emprunt a été crée avec succès"),
        @ApiResponse (responseCode = "400", description = "La requête n'est pas bonne (BAD REQUEST)"),
        @ApiResponse (responseCode = "409", description = "Une information entre en conflit (CONFLIT)"),
    })
    // PATCH /api/v1/emprunts/{id}/retour — Enregistrer le retour
    @PatchMapping("/{id}/retour")
    public ResponseEntity<EmpruntResponse> retourner(@PathVariable Long id) {
        Optional<Emprunt> resultat = empruntService.retourner(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();                       // 404
        }
        return ResponseEntity.ok(EmpruntMapper.versResponse(resultat.get())); // 200
    }

    @Operation (
        summary = "Supprimer un emprunt"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "204", description = "L'emprunt a été supprimé avec succès"),
        @ApiResponse (responseCode = "404", description = "L'emprunt n'existe pas"),
    })
    // DELETE /api/v1/emprunts/{id} — Supprimer un emprunt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (empruntService.supprimer(id)) {
            return ResponseEntity.noContent().build();                      // 204
        }
        return ResponseEntity.notFound().build();                           // 404
    }

    @Operation (
        summary = "Retourner les emprunts en retard"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Les emprunts en retard ont été retourné avec succès")
    })
    // GET /api/v1/emprunts/en-retard — Emprunts non rendus dont la date prévue est dépassée
    @GetMapping("/en-retard")
    public ResponseEntity<PagedModel<EmpruntResponse>> recupererEnRetard() {
        Page<Emprunt> retard = empruntService.recupererEnRetard();
        List<EmpruntResponse> contenu = EmpruntMapper.versResponses(retard.getContent());
        Page<EmpruntResponse> empruntDto = new PageImpl<>(contenu, retard.getPageable(), retard.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(empruntDto));
    }

    @Operation (
        summary = "Retourner les emprunts en cours"
    )
    @ApiResponses ({
        @ApiResponse (responseCode = "200", description = "Les emprunts ont été crée avec succès")
    })
    // GET /api/v1/emprunts/en-cours — Emprunts non rendus (paginés)
    @GetMapping("/en-cours")
    public ResponseEntity<PagedModel<EmpruntResponse>> recupererEnCours(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<Emprunt> page = empruntService.recupererEnCours(pageable);
        List<EmpruntResponse> contenu = EmpruntMapper.versResponses(page.getContent());
        Page<EmpruntResponse> empruntDto = new PageImpl<>(contenu, page.getPageable(), page.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(empruntDto));
    }
}
