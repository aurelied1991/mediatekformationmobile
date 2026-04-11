package com.example.mediatekformationmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediatekformationmobile.R;

/**
 * Activity principale affichant le menu de l'application. Elle permet d'accéder aux différentes
 * fonctionnalités : liste des formations et liste des favoris
 */
public class MainActivity extends AppCompatActivity {

    private ImageButton btnFormations;
    private ImageButton btnFavoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    /**
     * Initialise les composants et les actions du menu.
     */
    private void init(){
        chargeObjetsGraphiques();
        creerMenu();
    }

    /**
     * Récupère les éléments graphiques de l'interface.
     */
    private void chargeObjetsGraphiques(){
        btnFormations = findViewById(R.id.btnFormations);
        btnFavoris = findViewById(R.id.btnFavoris);
    }

    /**
     * Définit les actions associées aux boutons du menu.
     */
    private void creerMenu(){
        btnFormations.setOnClickListener(v -> ecouteMenu(FormationsActivity.class));
        btnFavoris.setOnClickListener(v -> ouvrirFavoris());
    }

    /**
     * Lance une activité générique passée en paramètre.
     * @param classe activité à ouvrir
     */
    private void ecouteMenu(Class<?> classe){
        Intent intent = new Intent(MainActivity.this, classe);
        startActivity(intent);
    }

    /**
     * Ouvre l'écran des formations en mode favoris.
     */
    private void ouvrirFavoris() {
        Intent intent = new Intent(MainActivity.this, FormationsActivity.class);
        intent.putExtra("mode", "favoris");
        startActivity(intent);
    }
}