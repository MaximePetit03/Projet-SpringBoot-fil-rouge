package com.biblio.Projet_fil_rouge.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblio.Projet_fil_rouge.exception.ConflitException;
import com.biblio.Projet_fil_rouge.exception.ResourceIntrouvableException;
import com.biblio.Projet_fil_rouge.model.Emprunt;
import com.biblio.Projet_fil_rouge.model.Livre;
import com.biblio.Projet_fil_rouge.repository.EmpruntRepository;
import com.biblio.Projet_fil_rouge.repository.LivreRepository;

/**
 * Logique métier des emprunts.
 * Un emprunt décrémente le stock du livre, un retour l'incrémente.
 * Les deux écritures sont dans la même transaction : tout ou rien.
 */
@Service
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;

    public EmpruntService(EmpruntRepository empruntRepository, LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
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

    /**
     * Emprunts non rendus dont la date de retour prévue est dépassée.
     */
    public List<Emprunt> recupererEnRetard() {
        Pageable tout = PageRequest.of(0, Integer.MAX_VALUE);
        return empruntRepository
                .findByRenduFalseAndDateRetourPrevueBefore(LocalDate.now(), tout)
                .getContent();
    }

    /**
     * Emprunts en cours : non rendus, quelle que soit la date de retour prévue.
     */
    public Page<Emprunt> recupererEnCours(Pageable pageable) {
        return empruntRepository.findByRenduFalse(pageable);
    }

    /**
     * Crée un emprunt et décrémente le stock du livre.
     * @throws IllegalArgumentException si la date de retour prévue est absente ou passée.
     * @throws ResourceIntrouvableException si le livre n'existe pas.
     * @throws ConflitException si le livre n'a plus d'exemplaire disponible.
     */
    @Transactional
    public Emprunt creer(Emprunt emprunt) {
        LocalDate retourPrevu = emprunt.getDateRetourPrevue();
        if (retourPrevu == null || retourPrevu.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La date de retour prévue est obligatoire et ne peut pas être dans le passé.");
        }

        Livre livre = trouverLivre(emprunt.getLivreId());
        decrementerStock(livre);
        livreRepository.save(livre);

        emprunt.setId(null);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setRendu(false);
        emprunt.setDateRetourEffective(null);
        return empruntRepository.save(emprunt);
    }

    /**
     * Marque un emprunt comme rendu et incrémente le stock du livre.
     * @return Optional vide si l'emprunt n'existe pas.
     * @throws ConflitException si l'emprunt a déjà été rendu.
     */
    @Transactional
    public Optional<Emprunt> retourner(Long id) {
        return empruntRepository.findById(id).map(emprunt -> {
            if (emprunt.isRendu()) {
                throw new ConflitException("Cet emprunt a déjà été rendu.");
            }

            Livre livre = trouverLivre(emprunt.getLivreId());
            incrementerStock(livre);
            livreRepository.save(livre);

            emprunt.setRendu(true);
            emprunt.setDateRetourEffective(LocalDate.now());
            return empruntRepository.save(emprunt);
        });
    }

    /**
     * Supprime un emprunt. Si le livre n'avait pas été rendu, son exemplaire
     * retourne en stock.
     * @return true si l'emprunt existait et a été supprimé, false sinon.
     */
    @Transactional
    public boolean supprimer(Long id) {
        Optional<Emprunt> emprunt = empruntRepository.findById(id);
        if (emprunt.isEmpty()) {
            return false;
        }
        if (!emprunt.get().isRendu()) {
            livreRepository.findById(emprunt.get().getLivreId()).ifPresent(livre -> {
                incrementerStock(livre);
                livreRepository.save(livre);
            });
        }
        empruntRepository.deleteById(id);
        return true;
    }

    // --- Gestion du stock ---

    private Livre trouverLivre(Long livreId) {
        return livreRepository.findById(livreId)
                .orElseThrow(() -> new ResourceIntrouvableException(
                        "Livre introuvable : " + livreId));
    }

    private void decrementerStock(Livre livre) {
        Integer stock = livre.getStock();
        if (stock == null || stock <= 0) {
            throw new ConflitException("Plus aucun exemplaire disponible pour ce livre.");
        }
        livre.setStock(stock - 1);
    }

    private void incrementerStock(Livre livre) {
        Integer stock = livre.getStock();
        livre.setStock(stock == null ? 1 : stock + 1);
    }
}