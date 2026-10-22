package com.biblio.Projet_fil_rouge.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entité JPA représentant un emprunt de livre.
 * Chaque instance correspond à une ligne dans la table "emprunts".
 */
@Entity
@Table(name = "emprunts")
public class Emprunt {

    /**
     * Identifiant unique, généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifiant du livre emprunté.
     */
    @NotNull(message = "Le livre emprunté est obligatoire")
    @Column(name = "livre_id", nullable = false)
    private Long livreId;

    /**
     * Nom de la personne qui emprunte le livre.
     */
    @NotBlank(message = "Le nom de l'emprunteur est obligatoire")
    @Column(nullable = false)
    private String emprunteur;

    /**
     * Adresse e-mail de l'emprunteur.
     */
    @NotBlank(message = "L'e-mail de l'emprunteur est obligatoire")
    @Email(message = "L'e-mail n'est pas valide")
    @Column(name = "email_emprunteur", nullable = false)
    private String emailEmprunteur;

    /**
     * Date de l'emprunt. Renseignée automatiquement à la création.
     */
    @Column(name = "date_emprunt", nullable = false)
    private LocalDate dateEmprunt = LocalDate.now();

    /**
     * Date prévue pour le retour du livre.
     */
    @NotNull(message = "La date de retour prévue est obligatoire")
    @Column(name = "date_retour_prevue", nullable = false)
    private LocalDate dateRetourPrevue;

    /**
     * Date réelle du retour. Vide tant que le livre n'est pas rendu.
     */
    @Column(name = "date_retour_effective")
    private LocalDate dateRetourEffective;

    /**
     * Indique si le livre a été rendu.
     */
    @Column(nullable = false)
    private boolean rendu = false;

    // --- Constructeurs ---

    /**
     * Constructeur sans arguments (requis par JPA).
     */
    public Emprunt() {
    }

    /**
     * Constructeur avec les champs saisis par l'utilisateur
     * (sans l'id, la date d'emprunt ni le statut de retour).
     */
    public Emprunt(Long livreId, String emprunteur, String emailEmprunteur,LocalDate dateRetourPrevue) {
        this.livreId = livreId;
        this.emprunteur = emprunteur;
        this.emailEmprunteur = emailEmprunteur;
        this.dateRetourPrevue = dateRetourPrevue;
    }

    // --- Getters et Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivreId() {
        return livreId;
    }

    public void setLivreId(Long livreId) {
        this.livreId = livreId;
    }

    public String getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    public String getEmailEmprunteur() {
        return emailEmprunteur;
    }

    public void setEmailEmprunteur(String emailEmprunteur) {
        this.emailEmprunteur = emailEmprunteur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public boolean isRendu() {
        return rendu;
    }

    public void setRendu(boolean rendu) {
        this.rendu = rendu;
    }

    @Override
    public String toString() {
        return "Emprunt{id=" + id + ", livreId=" + livreId
                + ", emprunteur='" + emprunteur + "', rendu=" + rendu + "}";
    }
}