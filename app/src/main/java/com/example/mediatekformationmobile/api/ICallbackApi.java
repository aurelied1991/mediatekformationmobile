package com.example.mediatekformationmobile.api;

/**
 * Interface de callback permettant de gérer les réponses de l'API. Elle permet de séparer le
 * traitement du succès et de l'erreur lors des appels réseau asynchrones.
 * @param <T> type de donnée retournée en cas de succès
 */
public interface ICallbackApi<T> {
    /**
     * Appelé lorsque la requête API réussit.
     * @param result données retournées par l'API
     */
    void onSuccess(T result);

    /**
     * Appelé lorsque la requête échoue.
     */
    void onError();
}
