package com.example.trivialpursuit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TrainingActivity extends AppCompatActivity {
    private Jeu jeu;
    private TextView tvCategorie;
    private TextView tvQuestion;
    private RadioGroup rgReponses;
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
        rgReponses = findViewById(R.id.rgReponses);
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

            String[] reponses = questionActuelle.getReponses();
            for (int i = 0; i < reponses.length; i++) {
                RadioButton rb = (RadioButton) rgReponses.getChildAt(i);
                rb.setText(reponses[i]);
            }
        } else {
            Toast.makeText(this, "Aucune question disponible pour cette catégorie", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void validerReponse() {
        int selectedId = rgReponses.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Veuillez sélectionner une réponse", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        int reponseIndex = rgReponses.indexOfChild(selectedRadioButton);

        if (jeu.verifierReponse(questionActuelle, reponseIndex)) {
            Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
            score++;
            tvScore.setText("Score: " + score);
        } else {
            Toast.makeText(this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
        }

        // Passer à la question suivante
        rgReponses.clearCheck();
        afficherQuestionAleatoire();
    }
} 