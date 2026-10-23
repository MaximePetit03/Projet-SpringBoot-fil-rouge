package com.biblio.Projet_fil_rouge.exception;

/**
 * Levée quand l'opération est impossible dans l'état actuel
 * (stock épuisé, emprunt déjà rendu...).
 */
public class ConflitException extends RuntimeException {

    public ConflitException(String message) {
        super(message);
    }
}