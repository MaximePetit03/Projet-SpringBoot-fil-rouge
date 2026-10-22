package com.biblio.Projet_fil_rouge.controller;

import com.biblio.Projet_fil_rouge.dto.LivreRequest;
import com.biblio.Projet_fil_rouge.dto.LivreResponse;
import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LivreController {

    private final LivreService livreService;
    private final LivreMapper livreMapper;

    public LivreController(LivreService livreService, LivreMapper livreMapper) {
        this.livreService = livreService;
        this.livreMapper = livreMapper;
    }

    @GetMapping("/livres")
    public ResponseEntity<Page<LivreResponse>> listerTous(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<LivreResponse> page = livreService.recupererTous(pageable)
                .map(livreMapper::versResponse);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/livres/{id}")
    public ResponseEntity<LivreResponse> obtenirParId(@PathVariable Long id) {
        return livreService.recupererParId(id)
                .map(livreMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 si inexistant
    }

    @GetMapping("/livres/disponibles")
    public ResponseEntity<Page<LivreResponse>> listerDisponibles(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<LivreResponse> page = livreService.listerStockDisponible(pageable)
                .map(livreMapper::versResponse);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/livres/recherche")
    public ResponseEntity<Page<LivreResponse>> rechercher(@RequestParam String titre, @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<LivreResponse> page = livreService.filtrer(titre, null, null, pageable)
                .map(livreMapper::versResponse);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/livres")
    public ResponseEntity<LivreResponse> creer(@Valid @RequestBody LivreRequest livreRequest) {
        Livre livreEntite = livreMapper.versEntite(livreRequest);
        Livre livreSauvegarde = livreService.creer(livreEntite);
        LivreResponse livreResponse = livreMapper.versResponse(livreSauvegarde);
        return ResponseEntity.status(HttpStatus.CREATED).body(livreResponse);
    }

    @PutMapping("/livres/{id}")
    public ResponseEntity<LivreResponse> mettreAJour(@PathVariable Long id, @Valid @RequestBody LivreRequest livreRequest) {
        Livre livreModifie = livreMapper.versEntite(livreRequest);
        return livreService.mettreAJour(id, livreModifie)
                .map(livreMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/livres/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        boolean supprime = livreService.supprimer(id);
        if (supprime) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/livres/{id}/stock")
    public ResponseEntity<LivreResponse> ajouterStock(@PathVariable Long id, @RequestParam Integer quantite) {
        return livreService.ajouterStock(id, quantite)
                .map(livreMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}