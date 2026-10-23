package com.biblio.Projet_fil_rouge.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.biblio.Projet_fil_rouge.dto.AuteurRequest;
import com.biblio.Projet_fil_rouge.dto.AuteurResponse;
import com.biblio.Projet_fil_rouge.model.Auteur;

public class AuteurMapper {
    public static Auteur versEntite(AuteurRequest request) {
        Auteur auteur = new Auteur();
        auteur.setNom(request.getNom());
        auteur.setPrenom(request.getPrenom());
        auteur.setNationalite(request.getNationalite());
        return auteur;
    }

    public static AuteurResponse versResponse(Auteur auteur) {
        return new AuteurResponse(
            auteur.getId(),
            auteur.getNom(),
            auteur.getPrenom(),
            auteur.getNationalite()
        );
    }
    
    public static List<AuteurResponse> versResponses(List<Auteur> auteurs) {
        if (auteurs == null) {
            return Collections.emptyList();
        }

        return auteurs.stream()
        .map(AuteurMapper::versResponse)
        .collect(Collectors.toList());
    }
}
