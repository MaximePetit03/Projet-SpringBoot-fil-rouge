package com.biblio.Projet_fil_rouge.repository;

import com.biblio.Projet_fil_rouge.model.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

    @Query("SELECT l FROM Livre l WHERE " +
            "(:titre IS NULL OR LOWER(l.titre) LIKE LOWER(CONCAT('%', :titre, '%'))) AND " +
            "(:prixMin IS NULL OR l.prix >= :prixMin) AND " +
            "(:prixMax IS NULL OR l.prix <= :prixMax)")
    Page<Livre> filtrer(@Param("titre") String titre,
                        @Param("prixMin") Double prixMin,
                        @Param("prixMax") Double prixMax,
                        Pageable pageable);


    Page<Livre> findByStockGreaterThan(int stock, Pageable pageable);

    Optional<Livre> findByIsbn(String isbn);
}
