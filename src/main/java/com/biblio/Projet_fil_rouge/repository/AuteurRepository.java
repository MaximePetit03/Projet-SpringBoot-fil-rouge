package com.biblio.Projet_fil_rouge.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Auteur;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    @Query("SELECT a FROM Auteur a WHERE a.nom = :nom")
    List<Auteur> trouverParNom(@Param("nom") String nom);
}
