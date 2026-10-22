package com.biblio.Projet_fil_rouge.mapper;


public class LivreMapper {
    private LivreMapper() {

    }
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

    public static List<LivreResponse> versResponses(List<Livre> livres) {
        if (livres == null) {
            return Collections.emptyList();
        }

        return livres.stream()
                .map(LivreMapper::versResponse)
                .collect(Collectors.toList());
    }
}
