package com.biblio.Projet_fil_rouge.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import jakarta.validation.Valid;

/**
 * Contrôleur REST pour les emprunts.
 * Entrées : EmpruntRequest. Sorties : EmpruntResponse.
 */
@RestController
@RequestMapping("/api/v1/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    // GET /api/v1/emprunts — Lister (paginé)
    @GetMapping
    public ResponseEntity<Page<EmpruntResponse>> listerTous(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<EmpruntResponse> page = empruntService.recupererTous(pageable)
                .map(EmpruntMapper::versResponse);
        return ResponseEntity.ok(page); // 200 OK
    }

    // GET /api/v1/emprunts/{id} — Obtenir un emprunt
    @GetMapping("/{id}")
    public ResponseEntity<EmpruntResponse> obtenirParId(@PathVariable Long id) {
        Optional<Emprunt> resultat = empruntService.recupererParId(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();                       // 404
        }
        return ResponseEntity.ok(EmpruntMapper.versResponse(resultat.get())); // 200
    }

    // POST /api/v1/emprunts — Créer un emprunt
    @PostMapping
    public ResponseEntity<EmpruntResponse> creer(@Valid @RequestBody EmpruntRequest request) {
        Emprunt empruntCree = empruntService.creer(EmpruntMapper.versEntite(request));
        URI emplacement = URI.create("/api/v1/emprunts/" + empruntCree.getId());
        return ResponseEntity
                .created(emplacement)                                       // 201 + Location
                .body(EmpruntMapper.versResponse(empruntCree));
    }

    // PATCH /api/v1/emprunts/{id}/retour — Enregistrer le retour
    @PatchMapping("/{id}/retour")
    public ResponseEntity<EmpruntResponse> retourner(@PathVariable Long id) {
        Optional<Emprunt> resultat = empruntService.retourner(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();                       // 404
        }
        return ResponseEntity.ok(EmpruntMapper.versResponse(resultat.get())); // 200
    }

    // DELETE /api/v1/emprunts/{id} — Supprimer un emprunt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (empruntService.supprimer(id)) {
            return ResponseEntity.noContent().build();                      // 204
        }
        return ResponseEntity.notFound().build();                           // 404
    }

    // GET /api/v1/emprunts/recherche?emprunteur=Lea — Emprunts d'une personne
    @GetMapping("/recherche")
    public ResponseEntity<List<EmpruntResponse>> rechercherParEmprunteur(
            @RequestParam String emprunteur) {
        List<EmpruntResponse> resultats = EmpruntMapper.versResponses(
                empruntService.recupererParEmprunteur(emprunteur));
        return ResponseEntity.ok(resultats);
    }

    // GET /api/v1/emprunts/en-retard — Emprunts non rendus et dépassés
    @GetMapping("/en-retard")
    public ResponseEntity<List<EmpruntResponse>> recupererEnRetard() {
        List<EmpruntResponse> resultats = EmpruntMapper.versResponses(
                empruntService.recupererEnRetard());
        return ResponseEntity.ok(resultats);
    }
}