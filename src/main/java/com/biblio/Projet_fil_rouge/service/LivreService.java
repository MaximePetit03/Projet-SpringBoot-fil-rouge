package com.biblio.Projet_fil_rouge.service;

import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public Page<Livre> recupererTous(Pageable pageable) {
        return livreRepository.findAll(pageable);
    }

    public Optional<Livre> recupererParId(Long id) {
        return livreRepository.findById(id);
    }

    public Livre creer(Livre livre) {
        livre.setId(null);

        if (livre.getIsbn() != null && livreRepository.findByIsbn(livre.getIsbn()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ISBN déjà utilisé");
        }

        if ((livre.getIsbn() != null && livre.getIsbn().length() > 20)
                || (livre.getTitre() != null && livre.getTitre().length() > 100)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ISBN ne doit pas dépasser 20 caractères ou le titre ne doit pas dépasser 100 caractères");
        }

        return livreRepository.save(livre);
    }

    public Optional<Livre> mettreAJour(Long id, Livre livreModifie) {
        return livreRepository.findById(id)
                .map(livreExistant -> {
                    if (livreModifie.getIsbn() != null
                            && !livreModifie.getIsbn().equals(livreExistant.getIsbn())
                            && livreRepository.findByIsbn(livreModifie.getIsbn()).isPresent()) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "ISBN déjà utilisé");
                    }

                    if ((livreModifie.getIsbn() != null && livreModifie.getIsbn().length() > 20)
                            || (livreModifie.getTitre() != null && livreModifie.getTitre().length() > 100)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ISBN ne doit pas dépasser 20 caractères ou le titre ne doit pas dépasser 100 caractères");
                    }

                    livreExistant.setTitre(livreModifie.getTitre());
                    livreExistant.setIsbn(livreModifie.getIsbn());
                    livreExistant.setPrix(livreModifie.getPrix());
                    livreExistant.setAnneePublication(livreModifie.getAnneePublication());
                    livreExistant.setStock(livreModifie.getStock());
                    livreExistant.setAuteurId(livreModifie.getAuteurId());
                    return livreRepository.save(livreExistant);
                });
    }

    public boolean supprimer(Long id) {
        if (livreRepository.existsById(id)) {
            livreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<Livre> filtrer(String titre, Double prixMin, Double prixMax, Pageable pageable) {
        return livreRepository.filtrer(titre, prixMin, prixMax, pageable);
    }

    public Page<Livre> listerStockDisponibles(Pageable pageable) {
        return livreRepository.findByStockGreaterThan(0, pageable);
    }

    public Optional<Livre> ajouterStock(Long id, Integer quantite) {
        return livreRepository.findById(id)
                .map(livreExistant -> {
                    int stockActuel = livreExistant.getStock() != null ? livreExistant.getStock() : 0;
                    int nouveauStock = stockActuel + quantite;

                    if (nouveauStock < 0) {
                        nouveauStock = 0;
                    }

                    livreExistant.setStock(nouveauStock);
                    return livreRepository.save(livreExistant);
                });
    }
}
