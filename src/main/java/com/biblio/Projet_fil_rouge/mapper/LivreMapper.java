package com.biblio.Projet_fil_rouge.mapper;


import com.biblio.Projet_fil_rouge.dto.LivreRequest;
import com.biblio.Projet_fil_rouge.dto.LivreResponse;
import com.biblio.Projet_fil_rouge.model.Livre;

public class LivreMapper {

    public static Livre versEntite(LivreRequest request) {
        Livre livre = new Livre();
        livre.setTitre(request.getTitre());
        livre.setIsbn(request.getIsbn());
        livre.setPrix(request.getPrix());
        livre.setDateDePublication(request.getDateDePublication());
        livre.setStock(request.getStock());
        livre.setAuteurId(request.getAuteurId());
    }

    public static LivreResponse versResponse(Livre livre) {
        return new LivreResponse(
                livre.getTitre(),
                livre.getIsbn(),
                livre.getPrix(),
                livre.getDateDePublication(),
                livre.getStock(),
                livre.getAuteurId()
        );
    }
}
