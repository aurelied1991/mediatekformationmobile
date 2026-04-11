package com.example.mediatekformationmobile.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe utilitaire centralisant les appels à l'API. Elle gère l'exécution des requêtes Retrofit
 * ainsi que le traitement générique des réponses (succès / erreur).
 */
public class HelperApi {

    private HelperApi() {
        // Empêche l'instanciation (classe utilitaire)
    }

    // Crée l'objet d'accès à l'api avec les différentes méthodes d'accès
    private static final IRequestApi api = FormationApi.getRetrofit() //récupère l'instance unique d'accès à l'api
            .create(IRequestApi.class); // crée une instance d'une classe ananyme qui implémente l'interface

    /**
     * Exécute une requête API de manière asynchrone.
     * @param call requête Retrofit à exécuter (méthode d'envoi)
     * @param callback gestionnaire de réponse (succès / erreur)
     * @param <T> type de données attendues dans le result
     */
    public static <T> void call(Call<ResponseApi<T>> call, ICallbackApi<T> callback) {
        call.enqueue(new Callback<ResponseApi<T>>() {
            @Override
            public void onResponse(Call<ResponseApi<T>> call, Response<ResponseApi<T>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.d("API",
                            "code: " + response.body().getCode()
                                    + " message: " + response.body().getMessage());

                    callback.onSuccess(response.body().getResult());

                } else {
                    Log.e("API", "Erreur API HTTP: " + response.code());
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<T>> call, Throwable throwable) {
                callback.onError();
                Log.e("API", "Erreur API", throwable);

            }
        });
    }

    /**
     * Retourne l'interface d'accès à l'API Retrofit.
     * @return IRequestApi instance unique
     */
    public static IRequestApi getApi(){
        return api;
    }
}
