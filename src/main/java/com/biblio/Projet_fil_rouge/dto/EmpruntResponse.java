package com.biblio.Projet_fil_rouge.dto;

public class EmpruntResponse {
    private Long id;
    private String emprunteur;
    private String emailEmprunteur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private boolean rendu = false;

    public EmpruntResponse() {
        this.id = id;
        this.emprunteur = emprunteur;
        this.emailEmprunteur = emailEmprunteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
        this.rendu = rendu;
    }

    public Long getId() {return id;}
    public String getEmprunteur() { return emprunteur;}
    public String getEmailEmprunteur() { return emailEmprunteur;}
    public LocalDate getDateEmprunt() {return dateEmprunt;}
    public LocalDate getDateRetourPrevue() {return dateRetourPrevue;}
    public LocalDate getDateRetourEffective() {return dateRetourEffective;}
    public boolean isRendu() {return rendu;}
}
