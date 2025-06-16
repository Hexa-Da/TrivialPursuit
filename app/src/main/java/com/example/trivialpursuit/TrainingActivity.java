package com.example.trivialpursuit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingActivity extends AppCompatActivity {
    private Jeu jeu;
    private TextView tvCategorie;
    private TextView tvQuestion;
    private EditText etReponse;
    private TextView tvScore;
    private Button btnValider;
    private Button btnRetour;
    private Question questionActuelle;
    private int score = 0;
    private String categorieSelectionnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // Récupération de la catégorie sélectionnée
        categorieSelectionnee = getIntent().getStringExtra("CATEGORIE");

        // Initialisation des vues
        tvCategorie = findViewById(R.id.tvCategorie);
        tvQuestion = findViewById(R.id.tvQuestion);
        etReponse = findViewById(R.id.etReponse);
        tvScore = findViewById(R.id.tvScore);
        btnValider = findViewById(R.id.btnValider);
        btnRetour = findViewById(R.id.btnRetour);

        // Initialisation du jeu
        jeu = new Jeu();

        // Affichage de la catégorie
        tvCategorie.setText("Catégorie: " + categorieSelectionnee);

        // Afficher la première question
        afficherQuestionAleatoire();

        // Configuration des boutons
        btnValider.setOnClickListener(v -> validerReponse());
        btnRetour.setOnClickListener(v -> finish());
    }

    private void afficherQuestionAleatoire() {
        questionActuelle = jeu.getQuestionAleatoire(categorieSelectionnee);

        if (questionActuelle != null) {
            tvQuestion.setText(questionActuelle.getQuestion());
            etReponse.setText(""); // Réinitialiser le champ de réponse
        } else {
            Toast.makeText(this, "Aucune question disponible pour cette catégorie", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void validerReponse() {
        String reponseJoueur = etReponse.getText().toString().trim();
        if (reponseJoueur.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une réponse", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean bonneReponse = jeu.verifierReponse(questionActuelle, reponseJoueur);

        // Afficher la bonne réponse dans tous les cas
        String message = bonneReponse ? 
            "Bonne réponse !" : 
            "Mauvaise réponse. La bonne réponse était : " + questionActuelle.getReponse();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        if (bonneReponse) {
            score++;
            tvScore.setText("Score: " + score);
        }

        // Afficher la prochaine question
        afficherQuestionAleatoire();
    }
} 