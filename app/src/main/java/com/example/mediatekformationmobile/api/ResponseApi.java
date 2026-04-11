package com.example.mediatekformationmobile.api;

/**
 * Classe est utilisée pour encapsuler les réponses reçues depuis le serveur afin d'uniformiser
 * le traitement des résultats (succès, erreurs, données).
 * @param <T> type générique correspondant au contenu de la réponse (result)
 */
public class ResponseApi<T> {
    /**
     * Code HTTP de la réponse (ex : 200 pour succès, 500 pour erreur serveur)
     */
    private int code;
    /**
     * Message associé à la réponse (succès ou description de l'erreur)
     */
    private String message;
    /**
     * Données retournées par l'API (différents types : objet, liste, entier, chaîne, null)
     */
    private T result;

    // Getters
    public int getCode() { return code; }
    public String getMessage() { return message; }
    public T getResult() { return result; }
}
