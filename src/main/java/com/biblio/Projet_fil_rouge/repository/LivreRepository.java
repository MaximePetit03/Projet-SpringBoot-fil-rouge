package com.biblio.Projet_fil_rouge.repository;

import com.biblio.Projet_fil_rouge.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface class LivreRepository extends JpaRepository<Livre, Long> {
    
}
