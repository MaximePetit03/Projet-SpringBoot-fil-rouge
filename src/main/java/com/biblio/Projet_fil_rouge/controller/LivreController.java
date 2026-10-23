package com.biblio.Projet_fil_rouge.controller;

import com.biblio.Projet_fil_rouge.dto.LivreRequest;
import com.biblio.Projet_fil_rouge.dto.LivreResponse;
import com.biblio.Projet_fil_rouge.mapper.LivreMapper;
import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService, LivreMapper livreMapper) {
        this.livreService = livreService;
    }

    @GetMapping("/livres")
    public ResponseEntity<Page<LivreResponse>> listerTous(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Livre> livresPage = livreService.recupererTous(pageable);
        Page<LivreResponse> responsePage = livresPage.map(livre -> LivreMapper.versResponse(livre));
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/livres/{id}")
    public ResponseEntity<LivreResponse> obtenirParId(@PathVariable Long id) {
        Optional<Livre> livreOpt = livreService.recupererParId(id);

        if (livreOpt.isPresent()) {
            Livre livre = livreOpt.get();
            LivreResponse livreResponse = LivreMapper.versResponse(livre);
            return ResponseEntity.ok(livreResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/livres/disponibles")
    public ResponseEntity<Page<LivreResponse>> listerDisponibles(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Livre> livresPage = livreService.listerStockDisponibles(pageable);
        Page<LivreResponse> responsePage = livresPage.map(livre -> LivreMapper.versResponse(livre));
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/livres/recherche")
    public ResponseEntity<Page<LivreResponse>> rechercher(@RequestParam String titre, @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Livre> livresPage = livreService.filtrer(titre, null, null, pageable);
        Page<LivreResponse> responsePage = livresPage.map(livre -> LivreMapper.versResponse(livre));
        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("/livres")
    public ResponseEntity<LivreResponse> creer(@Valid @RequestBody LivreRequest livreRequest) {
        Livre livreEntite = LivreMapper.versEntite(livreRequest);
        Livre livreSauvegarde = livreService.creer(livreEntite);
        LivreResponse livreResponse = LivreMapper.versResponse(livreSauvegarde);
        return ResponseEntity.status(HttpStatus.CREATED).body(livreResponse);
    }

    @PutMapping("/livres/{id}")
    public ResponseEntity<LivreResponse> mettreAJour(@PathVariable Long id, @Valid @RequestBody LivreRequest livreRequest) {
        Livre livreModifie = LivreMapper.versEntite(livreRequest);
        Optional<Livre> livreMisAJourOpt = livreService.mettreAJour(id, livreModifie);

        if (livreMisAJourOpt.isPresent()) {
            Livre livreMisAJour = livreMisAJourOpt.get();
            LivreResponse livreResponse = LivreMapper.versResponse(livreMisAJour);
            return ResponseEntity.ok(livreResponse);
        }

        return ResponseEntity.notFound().build();
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
        Optional<Livre> livreOpt = livreService.ajouterStock(id, quantite);

        if (livreOpt.isPresent()) {
            Livre livre = livreOpt.get();
            LivreResponse livreResponse = LivreMapper.versResponse(livre);
            return ResponseEntity.ok(livreResponse);
        }

        return ResponseEntity.notFound().build();
    }
}
