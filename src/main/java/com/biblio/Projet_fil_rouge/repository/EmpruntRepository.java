package com.biblio.Projet_fil_rouge.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblio.Projet_fil_rouge.model.Emprunt;


@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    List<Emprunt> trouverParEmprunt(String emprunteur);

}





