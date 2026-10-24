package com.biblio.Projet_fil_rouge.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.biblio.Projet_fil_rouge.dto.EmpruntRequest;
import com.biblio.Projet_fil_rouge.dto.EmpruntResponse;
import com.biblio.Projet_fil_rouge.model.Emprunt;

/**
 * Conversion entre DTOs et entité Emprunt.
 */
public final class EmpruntMapper {

    private EmpruntMapper() {
    }

    /**
     * DTO de requête → entité. Les champs gérés par le serveur (id, dateEmprunt, rendu)
     * prennent leur valeur par défaut.
     */
    public static Emprunt versEntite(EmpruntRequest request) {
        return new Emprunt(
                request.getLivreId(),
                request.getEmprunteur(),
                request.getEmailEmprunteur());
    }

    /** Entité → DTO de réponse. */
    public static EmpruntResponse versResponse(Emprunt emprunt) {
        boolean enRetard = false;

        if (LocalDate.now().isAfter(emprunt.getDateRetourPrevue())) {
            enRetard = true;
        }

        return new EmpruntResponse(
                emprunt.getId(),
                emprunt.getLivreId(),
                emprunt.getEmprunteur(),
                emprunt.getDateEmprunt(),
                emprunt.getDateRetourPrevue(),
                emprunt.getDateRetourEffective(),
                emprunt.isRendu(),
                enRetard
            );
    }

    /** Liste d'entités → liste de DTOs de réponse. */
    public static List<EmpruntResponse> versResponses(List<Emprunt> emprunts) {
        List<EmpruntResponse> resultat = new ArrayList<>();
        for (Emprunt emprunt : emprunts) {
            resultat.add(versResponse(emprunt));
        }
        return resultat;
    }
}
