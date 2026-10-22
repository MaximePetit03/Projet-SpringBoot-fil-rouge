package com.biblio.Projet_fil_rouge.repository;

import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Auteur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository 
public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    List<Auteur> trouverParNom(String nom);
   
}
