package com.biblio.Projet_fil_rouge.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblio.Projet_fil_rouge.model.Emprunt;
import com.biblio.Projet_fil_rouge.repository.EmpruntRepository;

/**
 * Logique métier des emprunts.
 * Le service applique les règles (date d'emprunt fixée par le serveur,
 * retour unique) et délègue l'accès aux données au repository.
 */
@Service
public class EmpruntService {

    private final EmpruntRepository empruntRepository;

    public EmpruntService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    public Page<Emprunt> recupererTous(Pageable pageable) {
        return empruntRepository.findAll(pageable);
    }

    public Optional<Emprunt> recupererParId(Long id) {
        return empruntRepository.findById(id);
    }

    public List<Emprunt> recupererParEmprunteur(String emprunteur) {
        return empruntRepository.findByEmprunteur(emprunteur);
    }

    public List<Emprunt> recupererEnRetard() {
        return empruntRepository.findByRenduFauxEtRetourPrevueAvant(LocalDate.now());
    }

    /**
     * Crée un emprunt. La date d'emprunt est fixée par le serveur, jamais par le client.
     * @throws IllegalArgumentException si la date de retour prévue est dans le passé.
     */
    public Emprunt creer(Emprunt emprunt) {
        if (emprunt.getDateRetourPrevue().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La date de retour prévue ne peut pas être dans le passé.");
        }
        emprunt.setId(null);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setRendu(false);
        emprunt.setDateRetourEffective(null);
        return empruntRepository.save(emprunt);
    }

    /**
     * Marque un emprunt comme rendu. Un emprunt ne peut être rendu qu'une fois.
     * @return Optional vide si l'emprunt n'existe pas.
     * @throws IllegalStateException si l'emprunt a déjà été rendu.
     */
    public Optional<Emprunt> retourner(Long id) {
        return empruntRepository.findById(id).map(emprunt -> {
            if (emprunt.isRendu()) {
                throw new IllegalStateException("Cet emprunt a déjà été rendu.");
            }
            emprunt.setRendu(true);
            emprunt.setDateRetourEffective(LocalDate.now());
            return empruntRepository.save(emprunt);
        });
    }

    /**
     * Supprime un emprunt par son ID.
     * @return true si l'emprunt existait et a été supprimé, false sinon.
     */
    public boolean supprimer(Long id) {
        if (empruntRepository.existsById(id)) {
            empruntRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
