package com.biblio.Projet_fil_rouge.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class LivreRequest {

    @NotBlank(message = "Le titre du livre est obligatoire")
    private String titre;

    @NotBlank(message = "L'isbn est obligatoire")
    private String isbn;

    @Positive(message = "Le prix doit être positif")
    private Double prix;

    @Positive (message = "L'année doit être positive")
    private Integer anneePublication;

    @PositiveOrZero(message = "Le stock ne peut pas être négatif")
    private Integer stock;

    @Positive(message = "L'ID de l'auteur doit être positif")
    private Long auteurId;

    public String getTitre() {return titre;}
    public void setTitre(String titre) {this.titre = titre;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public Double getPrix() {return prix;}
    public void setPrix(Double prix) {this.prix = prix;}

    public Integer getAnneePublication() {return anneePublication;}
    public void setAnneePublication(Integer anneePublication) {this.anneePublication = anneePublication;}

    public Integer getStock() {return stock;}
    public void setStock(Integer stock) {this.stock = stock;}

    public Long getAuteurId() {return auteurId;}
    public void setAuteurId(Long auteurId) {this.auteurId = auteurId;}

}
