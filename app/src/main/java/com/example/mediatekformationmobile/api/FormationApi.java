package com.example.mediatekformationmobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe de configuration de l'accès à l'API REST. Elle initialise une instance unique de Retrofit
 * (pattern singleton) et configure la conversion JSON via Gson.
 */
public class FormationApi {
    private FormationApi() {
        // Empêche l'instanciation
    }

    // 10.0.2.2 = localhost de la machine hôte quand on est dans l'émulateur Android
    private static final String API_URL = "http://mediatekformationmobile.atwebpages.com";

    private static Retrofit retrofit = null;

    /**
     * Configuration de Gson pour le parsing des dates.
     */
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * Retourne l'instance unique de Retrofit permettant d'accéder à l'API
     * @return instance Retrofit configurée
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            // crée l'objet d'accès à l'api
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL) // renseigne l'url de l'api
                    .addConverterFactory(GsonConverterFactory.create(gson)) // ajoute le convertisseur json
                    .build();
        }
        return retrofit;
    }

    /**
     * Retourne l'instance Gson utilisée pour la conversion JSON.
     * @return Gson configuré
     */
    public static Gson getGson() {
        return gson;
    }
}
