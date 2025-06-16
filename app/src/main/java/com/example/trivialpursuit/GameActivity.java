package com.example.trivialpursuit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private Jeu jeu;
    private TextView tvJoueurActuel;
    private TextView tvCategorie;
    private TextView tvQuestion;
    private RadioGroup rgReponses;
    private Button btnValider;
    private Question questionActuelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialisation des vues
        tvJoueurActuel = findViewById(R.id.tvJoueurActuel);
        tvCategorie = findViewById(R.id.tvCategorie);
        tvQuestion = findViewById(R.id.tvQuestion);
        rgReponses = findViewById(R.id.rgReponses);
        btnValider = findViewById(R.id.btnValider);

        // Initialisation du jeu
        jeu = new Jeu();
        // Ajouter des joueurs de test
        jeu.ajouterJoueur(new Joueur("Joueur 1"));
        jeu.ajouterJoueur(new Joueur("Joueur 2"));

        // Afficher la première question
        afficherQuestionAleatoire();

        // Configuration du bouton valider
        btnValider.setOnClickListener(v -> validerReponse());
    }

    private void afficherQuestionAleatoire() {
        String[] categories = jeu.getCategories();
        String categorieAleatoire = categories[(int) (Math.random() * categories.length)];
        questionActuelle = jeu.getQuestionAleatoire(categorieAleatoire);

        if (questionActuelle != null) {
            tvJoueurActuel.setText("Joueur actuel: " + jeu.getJoueurActuel().getNom());
            tvCategorie.setText("Catégorie: " + questionActuelle.getCategorie());
            tvQuestion.setText(questionActuelle.getQuestion());

            String[] reponses = questionActuelle.getReponses();
            for (int i = 0; i < reponses.length; i++) {
                RadioButton rb = (RadioButton) rgReponses.getChildAt(i);
                rb.setText(reponses[i]);
            }
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
            jeu.getJoueurActuel().ajouterPoints(1);
            jeu.getJoueurActuel().ajouterCategorie(questionActuelle.getCategorie());
        } else {
            Toast.makeText(this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
        }

        // Vérifier si le joueur a gagné
        if (jeu.getJoueurActuel().aGagneToutesCategories()) {
            Toast.makeText(this, jeu.getJoueurActuel().getNom() + " a gagné !", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Passer au joueur suivant
        jeu.passerAuJoueurSuivant();
        rgReponses.clearCheck();
        afficherQuestionAleatoire();
    }
} 