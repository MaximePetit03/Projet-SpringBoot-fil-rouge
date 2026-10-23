package com.biblio.Projet_fil_rouge.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Emprunt;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    Page<Emprunt> findByRenduFalse(Pageable pageable); // emprunts non rendus

    Page<Emprunt> findByRenduFalseAndDateRetourPrevueBefore(LocalDate date, Pageable pageable); // strictement avant "date"

    List<Emprunt> findByEmprunteur(String emprunteur); // à garder seulement si utilisé
}
