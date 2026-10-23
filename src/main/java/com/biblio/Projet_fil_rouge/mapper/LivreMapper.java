package com.biblio.Projet_fil_rouge.mapper;


import com.biblio.Projet_fil_rouge.dto.LivreRequest;
import com.biblio.Projet_fil_rouge.dto.LivreResponse;
import com.biblio.Projet_fil_rouge.model.Livre;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LivreMapper {
    private LivreMapper() {}

    public static Livre versEntite(LivreRequest request) {
        Livre livre = new Livre();
        livre.setTitre(request.getTitre());
        livre.setIsbn(request.getIsbn());
        livre.setPrix(request.getPrix());
        livre.setAnneePublication(request.getAnneePublication());
        livre.setStock(request.getStock());
        livre.setAuteurId(request.getAuteurId());

        return livre;
    }

    public static LivreResponse versResponse(Livre livre) {
        return new LivreResponse(
                livre.getId(),
                livre.getTitre(),
                livre.getIsbn(),
                livre.getPrix(),
                livre.getAnneePublication(),
                livre.getStock(),
                livre.getAuteurId()
        );
    }

    public static List<LivreResponse> versResponses(List<Livre> livres) {
        if (livres == null) {
            return Collections.emptyList();
        }

        return livres.stream()
                .map(LivreMapper::versResponse)
                .collect(Collectors.toList());
    }
}
