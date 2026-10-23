package com.biblio.Projet_fil_rouge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du livre est obligatoire")
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "L'isbn est obligatoire")
    @Column(nullable = false)
    private String isbn;

    @Positive(message = "Le prix doit être positif")
    @Column(nullable = false)
    private Double prix;

    @Positive (message = "L'année de publication doit être positive")
    @Column(name = "annee_publication")
    private Integer anneePublication;

    @PositiveOrZero(message = "Le stock ne peut pas être négatif")
    private Integer stock;

    @Positive(message = "L'ID de l'auteur doit être positif")
    @Column(nullable = false)
    private Long auteurId;

    public Livre() {}

    public Livre(String titre, String isbn, Double prix, Integer anneePublication, Integer stock, Long auteurId) {
        this.titre = titre;
        this.isbn = isbn;
        this.prix = prix;
        this.anneePublication = anneePublication;
        this.stock = stock;
        this.auteurId = auteurId;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(Long auteurId) {
        this.auteurId = auteurId;
    }
}
