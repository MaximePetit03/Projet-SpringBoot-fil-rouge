package com.biblio.Projet_fil_rouge.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class AuteurRequest {
    @NotBlank(message = "le nom est obligatoire")
    private String nom;
    @NotBlank(message = "le prenom est obligatoire")
    private String prenom;
    @NotBlank(message = "La nationalité est obligatoire")
    private String nationalite;

    public String getNom() {return nom;}
    public void SetNom(String nom) { this.nom = nom;}

    public String getPrenom() {return prenom;}
    public void SetPrenom( String prenom ) {this.prenom = prenom;}

    public String getNationalite() {return nationalite;}
    public void SetNationalite(String nationalite) {this.nationalite = nationalite;}
}
