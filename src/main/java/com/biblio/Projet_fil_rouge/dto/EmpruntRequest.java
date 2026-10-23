package com.biblio.Projet_fil_rouge.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de requête pour un emprunt : ce que le client envoie (POST).
 * Ni l'id, ni la date d'emprunt, ni le statut de retour : le serveur les gère.
 */
public class EmpruntRequest {

    @NotNull(message = "Le livre emprunté est obligatoire")
    private Long livreId;

    @NotBlank (message = "Le nom de l'emprunteur est obligatoire")
    @Size (max = 100, message = "Le nom de l'emprunteur ne doit pas dépasser 100 caractères")
    private String emprunteur;

    @NotBlank (message = "L'e-mail de l'emprunteur est obligatoire")
    @Email (message = "L'email doit être valide")
    private String emailEmprunteur;

    @NotNull(message = "La date de retour prévue est obligatoire")
    @FutureOrPresent (message = "La date de retour prévue ne peut pas être dans le passé")
    private LocalDate dateRetourPrevue;

    public EmpruntRequest() {
    }

    public Long getLivreId() { return livreId; }
    public void setLivreId(Long livreId) { this.livreId = livreId; }

    public String getEmprunteur() { return emprunteur; }
    public void setEmprunteur(String emprunteur) { this.emprunteur = emprunteur; }

    public String getEmailEmprunteur() { return emailEmprunteur; }
    public void setEmailEmprunteur(String emailEmprunteur) { this.emailEmprunteur = emailEmprunteur; }

    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
}
