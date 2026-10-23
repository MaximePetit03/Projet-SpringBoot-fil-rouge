package com.biblio.Projet_fil_rouge.exception;

/**
 * Levée quand une ressource référencée n'existe pas (ex. un livre inconnu).
 */
public class ResourceIntrouvableException extends RuntimeException {

    public ResourceIntrouvableException(String message) {
        super(message);
    }
}