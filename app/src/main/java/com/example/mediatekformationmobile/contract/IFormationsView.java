package com.example.mediatekformationmobile.contract;

import com.example.mediatekformationmobile.model.Formation;

import java.util.List;

/**
 * Contrat définissant les actions disponibles pour la vue liée à l'affichage des formations. Ce contrat
 * est utilisé dans le pattern MVP pour permettre au Presenter de communiquer avec l'interface utilisateur
 * sans dépendre de son implémentation concrète.
 */
public interface IFormationsView extends IAllView {
    /**
     * Méthode permettant d'afficher la liste des formations dans l'interface.
     * @param formations liste des formations à afficher
     */
    void afficherListe(List<Formation> formations);

    /**
     * Méthode permettant le transfert d'une formation vers une autre activity
     *
     * @param formation formation sélectionnée
     */
    public void transfertFormation(Formation formation);
}
