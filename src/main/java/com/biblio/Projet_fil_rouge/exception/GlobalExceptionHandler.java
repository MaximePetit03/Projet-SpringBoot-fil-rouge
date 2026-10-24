package com.biblio.Projet_fil_rouge.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Transforme les exceptions en réponses JSON structurées.
 * Les messages internes ne sont jamais renvoyés au client pour les erreurs serveur.
 */
@RestControllerAdvice
public class    GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> champs = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erreur ->
                champs.put(erreur.getField(), erreur.getDefaultMessage()));

        Map<String, Object> reponse = construireErreur(HttpStatus.BAD_REQUEST, "Validation échouée");
        reponse.put("details", champs);
        return ResponseEntity.badRequest().body(reponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleCorpsIllisible(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(construireErreur(HttpStatus.BAD_REQUEST, "Corps de requête invalide"));
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Map<String, Object>> handleTriInvalide(PropertyReferenceException ex) {
        return ResponseEntity.badRequest().body(construireErreur(HttpStatus.BAD_REQUEST,
                "Champ de tri inconnu : " + ex.getPropertyName()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleArgumentInvalide(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(construireErreur(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus statut = HttpStatus.valueOf(ex.getStatusCode().value());
        String message = ex.getReason() != null ? ex.getReason() : statut.getReasonPhrase();
        return ResponseEntity.status(statut)
                .body(construireErreur(statut, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        log.error("Erreur interne non prévue", ex); // détail dans les logs uniquement
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(construireErreur(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Une erreur interne est survenue"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        String message = "La route demandée n'existe pas ";
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(construireErreur(HttpStatus.NOT_FOUND, message));
    }
}
