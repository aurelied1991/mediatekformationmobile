package com.example.mediatekformationmobile.view;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediatekformationmobile.R;
import com.example.mediatekformationmobile.model.Formation;

/**
 * Activity permettant l'affichage d'une vidéo YouTube associée à une formation. Elle utilise une
 * WebView pour charger directement la vidéo à partir de l'identifiant YouTube fourni par la formation.
 */
public class VideoActivity extends AppCompatActivity {
    /**
     * Composant WebView utilisé pour afficher la vidéo.
     */
    WebView wbvYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    /**
     * Initialise l'affichage de la vidéo.
     */
    private void init(){
        recupFormation();
    }

    /**
     * Récupère la formation transmise par l'activité précédente et charge la vidéo associée dans
     * la WebView.
     */
    private void recupFormation(){
        Formation formation = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            formation = getIntent().getSerializableExtra("formation", Formation.class);
        }
        if(formation!=null) {
            wbvYoutube = findViewById(R.id.wbvYoutube);
            wbvYoutube.getSettings().setJavaScriptEnabled(true);
            wbvYoutube.setWebViewClient(new WebViewClient());
            wbvYoutube.loadUrl("https://www.youtube.com/watch?v=" + formation.getVideoId());
        }
    }
}