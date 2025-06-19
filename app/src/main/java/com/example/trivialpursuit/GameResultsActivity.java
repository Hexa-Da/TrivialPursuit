package com.example.trivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class GameResultsActivity extends AppCompatActivity {
    private LinearLayout containerResultats;
    private Button btnNouvellePartie;
    private Button btnRetourMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        // Initialisation des vues
        containerResultats = findViewById(R.id.containerResultats);
        btnNouvellePartie = findViewById(R.id.btnNouvellePartie);
        btnRetourMenu = findViewById(R.id.btnRetourMenu);

        // Récupérer les joueurs et leurs scores
        List<Joueur> joueurs = (List<Joueur>) getIntent().getSerializableExtra("JOUEURS");
        if (joueurs != null && !joueurs.isEmpty()) {
            // Trier les joueurs par nombre de catégories gagnées
            joueurs.sort((j1, j2) -> j2.getCategoriesGagnees().size() - j1.getCategoriesGagnees().size());

            // Afficher chaque joueur avec son score
            for (int i = 0; i < joueurs.size(); i++) {
                Joueur joueur = joueurs.get(i);
                afficherJoueur(joueur, i + 1);
            }
        }

        // Configuration des boutons
        btnNouvellePartie.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnRetourMenu.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void afficherJoueur(Joueur joueur, int position) {
        // Créer un conteneur pour le joueur
        LinearLayout joueurLayout = new LinearLayout(this);
        joueurLayout.setOrientation(LinearLayout.VERTICAL);
        joueurLayout.setPadding(0, 16, 0, 16);

        // Position et nom du joueur
        TextView tvNom = new TextView(this);
        String emoji = position == 1 ? "🥇" : position == 2 ? "🥈" : position == 3 ? "🥉" : "●";
        tvNom.setText(emoji + " " + joueur.getNom());
        tvNom.setTextSize(20);
        tvNom.setTextColor(getResources().getColor(android.R.color.black));
        tvNom.setPadding(0, 8, 0, 8);
        joueurLayout.addView(tvNom);

        // Score
        TextView tvScore = new TextView(this);
        tvScore.setText(joueur.getCategoriesGagnees().size() + " catégories gagnées");
        tvScore.setTextSize(16);
        tvScore.setPadding(0, 4, 0, 8);
        joueurLayout.addView(tvScore);

        // Catégories gagnées
        if (!joueur.getCategoriesGagnees().isEmpty()) {
            LinearLayout categoriesLayout = new LinearLayout(this);
            categoriesLayout.setOrientation(LinearLayout.HORIZONTAL);
            categoriesLayout.setPadding(0, 8, 0, 0);

            for (String categorie : joueur.getCategoriesGagnees()) {
                TextView tvCategorie = new TextView(this);
                tvCategorie.setText("●");
                tvCategorie.setTextSize(18);
                tvCategorie.setTextColor(getCouleurCategorie(categorie));
                tvCategorie.setPadding(4, 0, 4, 0);
                categoriesLayout.addView(tvCategorie);
            }
            joueurLayout.addView(categoriesLayout);
        }

        containerResultats.addView(joueurLayout);
    }

    private int getCouleurCategorie(String categorie) {
        for (Categorie cat : Categorie.TOUTES_CATEGORIES) {
            if (cat.getNom().equals(categorie)) {
                return cat.getCouleur();
            }
        }
        return 0xFF000000; // Noir par défaut
    }
} 