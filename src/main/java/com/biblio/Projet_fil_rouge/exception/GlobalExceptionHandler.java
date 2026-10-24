package com.biblio.Projet_fil_rouge.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Transforme les exceptions en réponses JSON structurées.
 * Les messages internes ne sont jamais renvoyés au client pour les erreurs serveur.
 */
@RestControllerAdvice
public class    GlobalExceptionHandler {

    private Map<String, Object> construireErreur(HttpStatus statut, String message) {
        Map<String, Object> erreur = new HashMap<>();
        erreur.put("timestamp", LocalDateTime.now().toString());
        erreur.put("statut", statut.value());
        erreur.put("erreur", statut.getReasonPhrase());
        erreur.put("message", message);
        return erreur;
    }

    @ExceptionHandler(ResourceIntrouvableException.class)
    public ResponseEntity<Map<String, Object>> handleIntrouvable(ResourceIntrouvableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(construireErreur(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(ConflitException.class)
    public ResponseEntity<Map<String, Object>> handleConflit(ConflitException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(construireErreur(HttpStatus.CONFLICT, ex.getMessage()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleArgumentInvalide(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(construireErreur(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        String message = "La route demandée n'existe pas ";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(construireErreur(HttpStatus.NOT_FOUND, message));
    }
}
