package com.example.mediatekformationmobile.contract;

/**
 * Interface de base pour toutes les vues de l'application. Elle définit les actions communes
 * pouvant être déclenchées par un Presenter (pattern MVP), comme l'affichage de messages.
 */
public interface IAllView {
    /**
     * Affiche un message à l'utilisateur (généralement via Toast).
     * @param message texte du message à afficher
     */
    void afficherMessage(String message);
}
