package com.biblio.Projet_fil_rouge.repository;

import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Auteur;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    Page<Auteur> trouverParNom(String nom);
   
}
