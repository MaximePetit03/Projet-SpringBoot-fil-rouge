package com.biblio.Projet_fil_rouge.service;

import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return livreRepository.save(livre);
    }

    public Optional<Livre> mettreAJour(Long id, Livre livreModifie) {
        return livreRepository.findById(id)
                .map(livreExistant -> {
                    livreExistant.setTitre(livreModifie.getTitre());
                    livreExistant.setIsbn(livreModifie.getIsbn());
                    livreExistant.setPrix(livreModifie.getPrix());
                    livreExistant.setDateDePublication(livreModifie.getDateDePublication());
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

    public Page<Livre> listerStockDisponible(Pageable pageable) {
        return livreRepository.stockDisponible(0, pageable);
    }
}