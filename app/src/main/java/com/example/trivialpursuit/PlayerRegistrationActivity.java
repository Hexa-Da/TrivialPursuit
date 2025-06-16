package com.example.trivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerRegistrationActivity extends AppCompatActivity {
    private TextView tvNombreJoueurs;
    private Button btnMoins, btnPlus, btnCommencer;
    private LinearLayout containerJoueurs;
    private int nombreJoueurs = 2;
    private List<EditText> champsJoueurs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_registration);

        // Initialisation des vues
        tvNombreJoueurs = findViewById(R.id.tvNombreJoueurs);
        btnMoins = findViewById(R.id.btnMoins);
        btnPlus = findViewById(R.id.btnPlus);
        btnCommencer = findViewById(R.id.btnCommencer);
        containerJoueurs = findViewById(R.id.containerJoueurs);

        // Configuration des boutons
        btnMoins.setOnClickListener(v -> modifierNombreJoueurs(-1));
        btnPlus.setOnClickListener(v -> modifierNombreJoueurs(1));
        btnCommencer.setOnClickListener(v -> commencerPartie());

        // Initialisation des champs de joueurs
        mettreAJourChampsJoueurs();
    }

    private void modifierNombreJoueurs(int delta) {
        int nouveauNombre = nombreJoueurs + delta;
        if (nouveauNombre >= 1 && nouveauNombre <= 6) {
            nombreJoueurs = nouveauNombre;
            tvNombreJoueurs.setText(String.valueOf(nombreJoueurs));
            mettreAJourChampsJoueurs();
        }
    }

    private void mettreAJourChampsJoueurs() {
        containerJoueurs.removeAllViews();
        champsJoueurs.clear();

        for (int i = 0; i < nombreJoueurs; i++) {
            View joueurView = LayoutInflater.from(this).inflate(R.layout.item_player_input, containerJoueurs, false);
            
            TextView tvJoueurNumero = joueurView.findViewById(R.id.tvJoueurNumero);
            EditText etNomJoueur = joueurView.findViewById(R.id.etNomJoueur);
            
            tvJoueurNumero.setText("Joueur " + (i + 1));
            champsJoueurs.add(etNomJoueur);
            
            containerJoueurs.addView(joueurView);
        }
    }

    private void commencerPartie() {
        List<String> nomsJoueurs = new ArrayList<>();
        boolean tousNomsRemplis = true;
        boolean nomsUniques = true;

        for (EditText champ : champsJoueurs) {
            String nom = champ.getText().toString().trim();
            if (nom.isEmpty()) {
                tousNomsRemplis = false;
                break;
            }
            if (nomsJoueurs.contains(nom)) {
                nomsUniques = false;
                break;
            }
            nomsJoueurs.add(nom);
        }

        if (!tousNomsRemplis) {
            Toast.makeText(this, "Veuillez remplir tous les noms des joueurs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nomsUniques) {
            Toast.makeText(this, "Les noms des joueurs doivent être différents", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer l'intent pour lancer le lancer de dé ou le jeu directement
        Intent intent;
        if (nombreJoueurs > 1) {
            intent = new Intent(this, DiceRollActivity.class);
        } else {
            intent = new Intent(this, GameActivity.class);
        }
        intent.putExtra("NOMS_JOUEURS", nomsJoueurs.toArray(new String[0]));
        startActivity(intent);
        finish();
    }
} 