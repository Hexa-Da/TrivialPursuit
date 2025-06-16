package com.example.trivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRollActivity extends AppCompatActivity {
    private TextView tvJoueurActuel;
    private TextView tvResultatDe;
    private Button btnLancerDe;
    private List<String> nomsJoueurs;
    private List<Integer> resultatsDes;
    private int joueurActuel = 0;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        // Initialisation des vues
        tvJoueurActuel = findViewById(R.id.tvJoueurActuel);
        tvResultatDe = findViewById(R.id.tvResultatDe);
        btnLancerDe = findViewById(R.id.btnLancerDe);

        // Récupération des noms des joueurs
        nomsJoueurs = new ArrayList<>();
        String[] joueurs = getIntent().getStringArrayExtra("NOMS_JOUEURS");
        if (joueurs != null) {
            for (String joueur : joueurs) {
                nomsJoueurs.add(joueur);
            }
        }
        resultatsDes = new ArrayList<>(nomsJoueurs.size());

        // Configuration du bouton
        btnLancerDe.setOnClickListener(v -> lancerDe());

        // Afficher le premier joueur
        mettreAJourAffichageJoueur();
    }

    private void mettreAJourAffichageJoueur() {
        if (joueurActuel < nomsJoueurs.size()) {
            tvJoueurActuel.setText("C'est au tour de : " + nomsJoueurs.get(joueurActuel));
            tvResultatDe.setText("?");
            btnLancerDe.setEnabled(true);
        }
    }

    private void lancerDe() {
        btnLancerDe.setEnabled(false);
        int resultat = random.nextInt(6) + 1;
        resultatsDes.add(resultat);
        tvResultatDe.setText(String.valueOf(resultat));

        // Attendre 2 secondes avant de passer au joueur suivant
        new Handler().postDelayed(() -> {
            joueurActuel++;
            if (joueurActuel < nomsJoueurs.size()) {
                mettreAJourAffichageJoueur();
            } else {
                // Tous les joueurs ont lancé le dé
                determinerPremierJoueur();
            }
        }, 2000);
    }

    private void determinerPremierJoueur() {
        int meilleurResultat = -1;
        int meilleurJoueur = 0;

        for (int i = 0; i < resultatsDes.size(); i++) {
            if (resultatsDes.get(i) > meilleurResultat) {
                meilleurResultat = resultatsDes.get(i);
                meilleurJoueur = i;
            }
        }

        // Créer l'intent pour lancer le jeu
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("NOMS_JOUEURS", nomsJoueurs.toArray(new String[0]));
        intent.putExtra("PREMIER_JOUEUR", meilleurJoueur);
        startActivity(intent);
        finish();
    }
} 