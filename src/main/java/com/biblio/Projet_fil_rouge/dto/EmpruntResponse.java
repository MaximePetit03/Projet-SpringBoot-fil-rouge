package com.biblio.Projet_fil_rouge.dto;

import java.time.LocalDate;

/**
 * DTO de réponse : ce que le client reçoit. Lecture seule.
 */
public class EmpruntResponse {

    private final Long id;
    private final Long livreId;
    private final String emprunteur;
    private final LocalDate dateEmprunt;
    private final LocalDate dateRetourPrevue;
    private final LocalDate dateRetourEffective;
    private final boolean rendu;

    public EmpruntResponse(Long id, Long livreId, String emprunteur,
                           LocalDate dateEmprunt, LocalDate dateRetourPrevue,
                           LocalDate dateRetourEffective, boolean rendu) {
        this.id = id;
        this.livreId = livreId;
        this.emprunteur = emprunteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
        this.rendu = rendu;
    }

    public Long getId() { return id; }
    public Long getLivreId() { return livreId; }
    public String getEmprunteur() { return emprunteur; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public boolean isRendu() { return rendu; }
}
