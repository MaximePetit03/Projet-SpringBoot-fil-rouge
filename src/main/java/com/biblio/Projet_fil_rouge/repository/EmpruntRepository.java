package com.biblio.Projet_fil_rouge.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Emprunt;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    /** Emprunts d'une personne. Équivalent SQL : WHERE emprunteur = ? */
    List<Emprunt> trouverparEmprunteur(String emprunteur);

    /** Emprunts en retard : non rendus, dont la date de retour prévue est dépassée. */
    List<Emprunt> trouverParRenduFauxEtRetourPrevueAvant(LocalDate date);
}