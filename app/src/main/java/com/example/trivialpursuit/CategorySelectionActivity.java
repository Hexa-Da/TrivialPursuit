package com.example.trivialpursuit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategorySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        // Initialisation des boutons
        Button btnGeographie = findViewById(R.id.btnGeographie);
        Button btnHistoire = findViewById(R.id.btnHistoire);
        Button btnDivertissement = findViewById(R.id.btnDivertissement);
        Button btnArtLitterature = findViewById(R.id.btnArtLitterature);
        Button btnSciencesNature = findViewById(R.id.btnSciencesNature);
        Button btnSportsLoisirs = findViewById(R.id.btnSportsLoisirs);
        Button btnRetour = findViewById(R.id.btnRetour);

        // Configuration des couleurs des boutons
        btnGeographie.setBackgroundColor(Categorie.GEOGRAPHIE.getCouleur());
        btnGeographie.setTextColor(Color.WHITE);
        
        btnHistoire.setBackgroundColor(Categorie.HISTOIRE.getCouleur());
        btnHistoire.setTextColor(Color.WHITE);
        
        btnDivertissement.setBackgroundColor(Categorie.DIVERTISSEMENT.getCouleur());
        btnDivertissement.setTextColor(Color.BLACK);
        
        btnArtLitterature.setBackgroundColor(Categorie.ART_LITTERATURE.getCouleur());
        btnArtLitterature.setTextColor(Color.WHITE);
        
        btnSciencesNature.setBackgroundColor(Categorie.SCIENCES_NATURE.getCouleur());
        btnSciencesNature.setTextColor(Color.WHITE);
        
        btnSportsLoisirs.setBackgroundColor(Categorie.SPORTS_LOISIRS.getCouleur());
        btnSportsLoisirs.setTextColor(Color.BLACK);

        // Configuration des listeners
        btnGeographie.setOnClickListener(v -> lancerEntrainement("Géographie"));
        btnHistoire.setOnClickListener(v -> lancerEntrainement("Histoire"));
        btnDivertissement.setOnClickListener(v -> lancerEntrainement("Divertissement"));
        btnArtLitterature.setOnClickListener(v -> lancerEntrainement("Art et Littérature"));
        btnSciencesNature.setOnClickListener(v -> lancerEntrainement("Sciences et Nature"));
        btnSportsLoisirs.setOnClickListener(v -> lancerEntrainement("Sports et Loisirs"));
        btnRetour.setOnClickListener(v -> finish());
    }

    private void lancerEntrainement(String categorie) {
        Intent intent = new Intent(this, TrainingActivity.class);
        intent.putExtra("CATEGORIE", categorie);
        startActivity(intent);
    }
} 