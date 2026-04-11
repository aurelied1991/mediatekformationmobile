package com.example.mediatekformationmobile.api;

import com.example.mediatekformationmobile.model.Formation;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface Retrofit définissant les requêtes HTTP disponibles vers l'API. Elle sert de contrat
 * entre l'application Android et le serveur REST. Chaque méthode correspond à un endpoint de l'API.
 */
public interface IRequestApi {
    /**
     * Récupère la liste des formations disponibles. Endpoint : GET /formation
     * le nom de la table ("formation") est ajouté à l'url
     * @return objet Call contenant une ResponseApi avec une liste de Formation
     */
    @GET("formation")
    Call<ResponseApi<List<Formation>>> getFormations();
}
