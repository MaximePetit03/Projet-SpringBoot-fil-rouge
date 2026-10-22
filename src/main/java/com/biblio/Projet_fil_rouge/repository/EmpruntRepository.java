package com.biblio.Projet_fil_rouge.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Emprunt;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    /** Emprunts d'une personne. Équivalent SQL : WHERE emprunteur = ? */
    @Query("SELECT e FROM Emprunt e WHERE e.emprunteur = :emprunteur")
    List<Emprunt> trouverparEmprunteur(@Param("emprunteur") String emprunteur);

    /** Emprunts en retard : non rendus, dont la date de retour prévue est dépassée. */
    @Query("SELECT e FROM Emprunt e WHERE e.rendu = false AND e.dateRetourPrevue < :date")
    List<Emprunt> trouverParRenduFauxEtRetourPrevueAvant(@Param("date") LocalDate date);
}