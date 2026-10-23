package com.biblio.Projet_fil_rouge.controller;

import com.biblio.Projet_fil_rouge.dto.LivreRequest;
import com.biblio.Projet_fil_rouge.dto.LivreResponse;
import com.biblio.Projet_fil_rouge.mapper.LivreMapper;
import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.service.LivreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Gestion des livres", description = "Endpoints pour la gestion et la recherche des livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping("/livres")
    @Operation(summary = "Lister tous les livres", description = "Récupère la liste paginée de tous les livres disponibles")
    @ApiResponse(responseCode = "200", description = "Liste paginée des livres récupérée avec succès")
    public ResponseEntity<PagedModel<LivreResponse>> listerTous(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Livre> livresPage = livreService.recupererTous(pageable);
        Page<LivreResponse> responsePage = livresPage.map(livre -> LivreMapper.versResponse(livre));
        return ResponseEntity.ok(new PagedModel<>(responsePage));
    }

    @GetMapping("/livres/{id}")
    @Operation(summary = "Obtenir un livre par son Id", description = "Récupère les détails d'un livre grâce à son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
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
    @Operation(summary = "Lister les livres en stock", description = "Récupère la liste paginée des livres dont le stock est supérieur à zéro.")
    @ApiResponse(responseCode = "200", description = "Liste des livres disponibles récupérée avec succès")
    public ResponseEntity<PagedModel<LivreResponse>> listerDisponibles(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Livre> livresPage = livreService.listerStockDisponibles(pageable);
        Page<LivreResponse> responsePage = livresPage.map(livre -> LivreMapper.versResponse(livre));
        return ResponseEntity.ok(new PagedModel<>(responsePage));
    }

    @GetMapping("/livres/recherche")
    @Operation(summary = "Rechercher des livres par titre", description = "Permet de filtrer et rechercher des livres par leur titre")
    @ApiResponse(responseCode = "200", description = "Résultats de la recherche récupérés avec succès")
    public ResponseEntity<PagedModel<LivreResponse>> rechercher(@RequestParam String titre, @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Livre> livresPage = livreService.filtrer(titre, null, null, pageable);
        Page<LivreResponse> responsePage = livresPage.map(livre -> LivreMapper.versResponse(livre));
        return ResponseEntity.ok(new PagedModel<>(responsePage));
    }

    @PostMapping("/livres")
    @Operation(summary = "Créer un nouveau livre", description = "Ajoute un nouveau livre dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livre créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données de la requête invalides"),
            @ApiResponse(responseCode = "409", description = "Conflit (ex: ISBN déjà existant)")
    })
    public ResponseEntity<LivreResponse> creer(@Valid @RequestBody LivreRequest livreRequest) {
        Livre livreEntite = LivreMapper.versEntite(livreRequest);
        Livre livreSauvegarde = livreService.creer(livreEntite);
        LivreResponse livreResponse = LivreMapper.versResponse(livreSauvegarde);
        return ResponseEntity.status(HttpStatus.CREATED).body(livreResponse);
    }

    @PutMapping("/livres/{id}")
    @Operation(summary = "Mettre à jour un livre", description = "Met à jour l'ensemble des informations d'un livre existant précis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
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
    @Operation(summary = "Supprimer un livre", description = "Supprime définitivement un livre de la base de données via son Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livre supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        boolean supprime = livreService.supprimer(id);
        if (supprime) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/livres/{id}/stock")
    @Operation(summary = "Ajouter ou modifier le stock", description = "Modifie la quantité en stock d'un livre spécifique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
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