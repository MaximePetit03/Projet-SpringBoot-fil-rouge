package com.biblio.Projet_fil_rouge.dto;


public class LivreResponse {
    private Long id;
    private String titre;
    private String isbn;
    private Double prix;
    private Integer dateDePublication;
    private Integer stock;
    private Long auteurId;
    

    public LivreResponse(Long id, String titre, String isbn, Double prix, Integer dateDePublication, Integer stock, Long auteurId) {
        this.id = id;
        this.titre = titre;
        this.isbn = isbn;
        this.prix = prix;
        this.dateDePublication = dateDePublication;
        this.stock = stock;
        this.auteurId = auteurId;
    }

    public Long getId() {return id;}
    public String getTitre() {return titre;}
    public String getIsbn() {return isbn;}
    public Double getPrix() {return prix;}
    public Integer getDateDePublication() {return dateDePublication;}
    public Integer getStock() {return stock;}
    public Long getAuteurId() {return auteurId;}
}
