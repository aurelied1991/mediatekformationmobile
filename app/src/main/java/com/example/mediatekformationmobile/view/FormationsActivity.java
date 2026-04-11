package com.example.mediatekformationmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.contract.IFormationsView;
import com.example.mediatekformationmobile.data.FormationDAO;
import com.example.mediatekformationmobile.model.Formation;
import com.example.mediatekformationmobile.presenter.FormationsPresenter;

import java.util.List;

/**
 * Activity principale affichant la liste des formations. Elle implémente le contrat IFormationsView
 * afin de recevoir les données du Presenter et de gérer l'affichage UI.
 */
public class FormationsActivity extends AppCompatActivity implements IFormationsView {

    private FormationsPresenter presenter;
    private boolean modeFavoris = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    /**
     * Initialise les composants de l'activité et le Presenter.
     */
    private void init(){
        presenter = new FormationsPresenter(this);
        presenter.chargerFormations();
        findViewById(R.id.btnFiltrer).setOnClickListener(v -> clicFiltrer());
        if (getIntent().hasExtra("mode")) {
            modeFavoris = getIntent().getStringExtra("mode").equals("favoris");
        }
    }

    /**
     * Méthode permettant d'afficher la liste des formations dans le RecyclerView. Si l'activité est
     * en mode favoris, seules les formations enregistrées localement sont conservées.
     * @param formations liste des formations à afficher
     */
    @Override
    public void afficherListe(List<Formation> formations) {
        if (modeFavoris) {
            FormationDAO dao = new FormationDAO(this);
            List<Integer> idsFavoris = dao.getIdFavoris();
            List<Formation> formationsFavories = new java.util.ArrayList<>();

            for (Formation formation : formations) {
                if (idsFavoris.contains(formation.getId())) {
                    formationsFavories.add(formation);
                }
            }

            formations = formationsFavories;
        }
        if (formations != null){
            RecyclerView lstHisto = (RecyclerView) findViewById(R.id.lstFormations);
            FormationListAdapter adapter = new FormationListAdapter(formations, FormationsActivity.this, modeFavoris);
            lstHisto.setAdapter(adapter);
            lstHisto.setLayoutManager(new LinearLayoutManager(FormationsActivity.this));
        }
    }

    /**
     * Méthode permettant le transfert d'une formation vers une activity
     * @param formation
     */
    @Override
    public void transfertFormation(Formation formation) {
        Intent intent = new Intent(FormationsActivity.this, UneFormationActivity.class);
        intent.putExtra("formation", formation);
        startActivity(intent);
    }

    /**
     * Récupère le texte saisi dans le champ de filtre et demande au Presenter d'appliquer le filtrage.
     */
    private void clicFiltrer() {
        EditText txtFiltre = findViewById(R.id.txtFiltre);
        String filtre = txtFiltre.getText().toString();
        presenter.filtrerFormations(filtre);
    }

    /**
     * Méthode permettant d'afficher un message de type Toast
     * @param message
     */
    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}