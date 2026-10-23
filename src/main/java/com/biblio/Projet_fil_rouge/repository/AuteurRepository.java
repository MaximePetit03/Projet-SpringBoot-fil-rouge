package com.biblio.Projet_fil_rouge.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Auteur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    @Query("SELECT a FROM Auteur a WHERE a.nom = :nom")
    Page<Auteur> trouverParNom(@Param("nom") String nom, Pageable pageable);
}
