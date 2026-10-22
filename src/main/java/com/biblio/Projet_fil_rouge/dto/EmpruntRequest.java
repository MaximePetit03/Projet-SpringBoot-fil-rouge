package com.biblio.Projet_fil_rouge.dto;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpruntRequest {

    @NotNull (message = "Le livre emprunté est obligatoire")
    private Long livreId;

    @NotBlank (message = "Le nom de l'emprunteur est obligatoire")
    @Size (max = 100, message = "Le nom de l'emprunteur ne doit pas dépasser 100 caractères")
    private String emprunteur;

    @NotBlank (message = "L'e-mail de l'emprunteur est obligatoire")
    @Email (message = "L'email doit être valide")
    private String emailEmprunteur;

    private LocalDate dateEmprunt;

    @NotNull (message = "La date de retour prévue est obligatoire")
    private LocalDate dateRetourPrevue;


    private LocalDate dateRetourEffective;
    private boolean rendu = false;

    public Long getLivreId() {return livreId;}
    public void SetLivreID(Long livreId) {this.livreId = livreId;}

    public String getEmprunteur() {return emprunteur;}
    public void setEmprunteur(String emprunteur) {this.emprunteur = emprunteur;}

    public String getEmailEmprunteur() {return emailEmprunteur;}
    public void setEmailEmprunteur(String emailEmprunteur) {this.emailEmprunteur = emailEmprunteur;}

    public LocalDate getDateEmprunt() {return dateEmprunt;}
    public void setDateEmprunt(LocalDate dateEmprunt) {this.dateEmprunt = dateEmprunt;}

    public LocalDate getDateRetourPrevue() {return dateRetourPrevue;}
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {this.dateRetourPrevue = dateRetourPrevue;}

    public LocalDate getDateRetourEffective() {return dateRetourEffective;}
    public void setDateRetourEffective(LocalDate dateRetourEffective) {this.dateRetourEffective = dateRetourEffective;}

    public boolean isRendu() {return rendu;}
    public void setRendu(boolean rendu) {this.rendu = rendu;}
}
