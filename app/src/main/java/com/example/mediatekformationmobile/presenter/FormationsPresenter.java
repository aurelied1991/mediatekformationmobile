package com.example.mediatekformationmobile.presenter;

import android.content.Context;

import com.example.mediatekformationmobile.api.HelperApi;
import com.example.mediatekformationmobile.api.ICallbackApi;
import com.example.mediatekformationmobile.contract.IFormationsView;
import com.example.mediatekformationmobile.data.FormationDAO;
import com.example.mediatekformationmobile.model.Formation;

import java.util.Collections;
import java.util.List;

/**
 * Presenter gérant la logique métier de l'écran affichant la liste des formations (FormationsActivity).
 * Il fait le lien entre la vue et les sources de données (API distante et stockage local).
 */
public class FormationsPresenter {
    private IFormationsView vue;
    private List<Formation> allFormations;

    /**
     * Constructeur : initialise le presenter avec la vue associée.
     * @param vue interface permettant la communication avec l'UI
     */
    public FormationsPresenter(IFormationsView vue){
        this.vue = vue;
    }

    /**
     * Charge les formations depuis l'API distante, met à jour les favoris locaux et envoie les données à la vue.
     */
    public void chargerFormations() {
        // sollicite l'api et récupère la réponse
        HelperApi.call(HelperApi.getApi().getFormations(), new ICallbackApi<List<Formation>>(){
            @Override
            public void onSuccess(List<Formation> result) {
                if(result != null && !result.isEmpty()){
                    allFormations = result;
                    try (FormationDAO dao = new FormationDAO((Context) vue)) {
                        dao.nettoyerFavorisObsoletes(allFormations);
                    }
                    Collections.sort(allFormations, (p1, p2) -> p2.getPublishedAt().compareTo(p1.getPublishedAt()));
                    vue.afficherListe(allFormations);
                }else{
                    vue.afficherMessage("Aucune formation disponible");
                }
            }
            @Override
            public void onError() {
                vue.afficherMessage("Erreur lors du chargement des formations");
            }
        });
    }

    /**
     * Filtre les formations en fonction du texte saisi par l'utilisateur.
     * @param filtre texte de recherche
     */
    public void filtrerFormations(String filtre) {
        if (allFormations == null) {
            vue.afficherMessage("Aucune formation chargée");
            return;
        }
        // si filtre vide = tout afficher
        if (filtre == null || filtre.trim().isEmpty()) {
            vue.afficherListe(allFormations);
            return;
        }
        List<Formation> formationsFiltrees = new java.util.ArrayList<>();
        for (Formation formation : allFormations) {
            if (formation.getTitle().toLowerCase()
                    .contains(filtre.toLowerCase())) {
                formationsFiltrees.add(formation);
            }
        }
        vue.afficherListe(formationsFiltrees);
    }

    /**
     * Transfère une formation sélectionnée vers une autre activité.
     * @param formation formation sélectionnée
     */
    public void transfertFormation(Formation formation){
        vue.transfertFormation(formation);
    }
}
