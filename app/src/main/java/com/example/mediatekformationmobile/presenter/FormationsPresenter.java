package com.example.mediatekformationmobile.presenter;

import com.example.mediatekformationmobile.api.HelperApi;
import com.example.mediatekformationmobile.api.ICallbackApi;
import com.example.mediatekformationmobile.contract.IFormationsView;
import com.example.mediatekformationmobile.model.Formation;

import java.util.Collections;
import java.util.List;

/**
 * 'presenter dédié' à la vue qui affiche la liste des formations (FormationsActivity)
 */
public class FormationsPresenter {
    private IFormationsView vue;
    private List<Formation> allFormations;

    /**
     * Constructeur : valorise la propriété qui permet d'accéder à la vue
     * @param vue
     */
    public FormationsPresenter(IFormationsView vue){
        this.vue = vue;
    }

    /**
     * Récupère les formations de la BDD distante et les envoie à la vue
     */
    public void chargerFormations() {
        // sollicite l'api et récupère la réponse
        HelperApi.call(HelperApi.getApi().getFormations(), new ICallbackApi<List<Formation>>(){
            @Override
            public void onSuccess(List<Formation> result) {
                if(result != null && !result.isEmpty()){
                    allFormations = result;
                    Collections.sort(allFormations, (p1, p2) -> p2.getPublishedAt().compareTo(p1.getPublishedAt()));
                    vue.afficherListe(allFormations);
                }else{
                    vue.afficherMessage("échec chargement formations");
                }
            }
            @Override
            public void onError() {
                vue.afficherMessage("échec chargement formations");
            }
        });
    }

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
     * Demnde de transfert de la formation vers une autre activity
     * @param formation
     */
    public void transfertFormation(Formation formation){
        vue.transfertFormation(formation);
    }
}
