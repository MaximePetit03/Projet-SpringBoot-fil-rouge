package com.biblio.Projet_fil_rouge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblio.Projet_fil_rouge.model.Auteur;
import com.biblio.Projet_fil_rouge.repository.AuteurRepository;

@Service 
public class AuteurService {
    private AuteurRepository auteurRepository;

    public AuteurService(AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    public Page<Auteur> recupererTous(Pageable pageable) {
        return auteurRepository.findAll(pageable);
    }

    public Optional<Auteur> recupererParId(Long id) {
        return auteurRepository.findById(id);
    }

    public Auteur creerAuteur(Auteur auteur) {
        auteur.setId(null);
        return auteurRepository.save(auteur);
    }

    public Optional<Auteur> updateAuteur(Long id, Auteur auteur) {
        return auteurRepository.findById(id)
        .map(auteurExistant -> {
            auteurExistant.setNom(auteur.getNom());
            auteurExistant.setPrenom(auteur.getPrenom());
            auteurExistant.setNationalite(auteur.getNationalite());
            return auteurRepository.save(auteurExistant);
        });
    }

    public boolean supprimerAuteur(Long id) {
        if (auteurRepository.existsById(id)) {
            auteurRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<Auteur> trouverAuteurs(String nom) {
        return auteurRepository.trouverParNom(nom);
    }
}
