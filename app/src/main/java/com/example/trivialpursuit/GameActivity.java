package com.example.trivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private Jeu jeu;
    private TextView tvJoueurActuel;
    private TextView tvCategorie;
    private TextView tvQuestion;
    private EditText etReponse;
    private Button btnValider;
    private Question questionActuelle;
    private List<String> categoriesDisponibles;
    private boolean premierTour = true;
    private LinearLayout containerScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialisation des vues
        tvJoueurActuel = findViewById(R.id.tvJoueurActuel);
        tvCategorie = findViewById(R.id.tvCategorie);
        tvQuestion = findViewById(R.id.tvQuestion);
        etReponse = findViewById(R.id.etReponse);
        btnValider = findViewById(R.id.btnValider);
        containerScores = findViewById(R.id.containerScores);

        // Initialisation du jeu
        jeu = new Jeu();
        categoriesDisponibles = new ArrayList<>();

        // Récupération des noms des joueurs
        String[] nomsJoueurs = getIntent().getStringArrayExtra("NOMS_JOUEURS");
        if (nomsJoueurs != null) {
            for (String nom : nomsJoueurs) {
                jeu.ajouterJoueur(new Joueur(nom));
            }
        }

        // Réinitialiser les catégories disponibles
        resetCategoriesDisponibles();

        // Si plus d'un joueur, récupérer le premier joueur
        if (jeu.getNombreJoueurs() > 1) {
            int premierJoueur = getIntent().getIntExtra("PREMIER_JOUEUR", 0);
            jeu.setJoueurActuel(premierJoueur);
        }

        // Initialiser l'affichage des scores
        initialiserScores();

        // Afficher la première question
        afficherQuestionAleatoire();

        // Configuration du bouton valider
        btnValider.setOnClickListener(v -> validerReponse());
    }

    private void initialiserScores() {
        containerScores.removeAllViews();
        for (Joueur joueur : jeu.getJoueurs()) {
            LinearLayout joueurLayout = new LinearLayout(this);
            joueurLayout.setOrientation(LinearLayout.HORIZONTAL);
            joueurLayout.setPadding(0, 8, 0, 8);

            TextView tvNom = new TextView(this);
            tvNom.setText(joueur.getNom() + " : ");
            tvNom.setTextSize(16);
            joueurLayout.addView(tvNom);

            // Ajouter les points de couleur pour chaque catégorie gagnée
            for (String categorie : joueur.getCategoriesGagnees()) {
                TextView tvPoint = new TextView(this);
                tvPoint.setText("●");
                tvPoint.setTextSize(20);
                tvPoint.setTextColor(getCouleurCategorie(categorie));
                tvPoint.setPadding(4, 0, 4, 0);
                joueurLayout.addView(tvPoint);
            }

            containerScores.addView(joueurLayout);
        }
    }

    private int getCouleurCategorie(String categorie) {
        switch (categorie) {
            case "Géographie": return Categorie.GEOGRAPHIE.getCouleur();
            case "Histoire": return Categorie.HISTOIRE.getCouleur();
            case "Divertissement": return Categorie.DIVERTISSEMENT.getCouleur();
            case "Art et Littérature": return Categorie.ART_LITTERATURE.getCouleur();
            case "Sciences et Nature": return Categorie.SCIENCES_NATURE.getCouleur();
            case "Sports et Loisirs": return Categorie.SPORTS_LOISIRS.getCouleur();
            default: return 0xFF000000; // Noir par défaut
        }
    }

    private void mettreAJourScores() {
        containerScores.removeAllViews();
        initialiserScores();
    }

    private void resetCategoriesDisponibles() {
        categoriesDisponibles.clear();
        for (String categorie : jeu.getCategories()) {
            categoriesDisponibles.add(categorie);
        }
    }

    private void afficherQuestionAleatoire() {
        Joueur joueurActuel = jeu.getJoueurActuel();
        String[] categoriesDisponibles = jeu.getCategoriesDisponibles(joueurActuel);
        
        if (categoriesDisponibles.length == 0) {
            // Le joueur actuel a gagné toutes les catégories
            afficherResultats();
            return;
        }

        // Sélectionner une catégorie aléatoire parmi les disponibles
        Random random = new Random();
        String categorieAleatoire = categoriesDisponibles[random.nextInt(categoriesDisponibles.length)];
        
        questionActuelle = jeu.getQuestionAleatoire(categorieAleatoire);

        if (questionActuelle != null) {
            tvJoueurActuel.setText("C'est au tour de : " + joueurActuel.getNom());
            tvCategorie.setText("Catégorie : " + questionActuelle.getCategorie());
            tvQuestion.setText(questionActuelle.getQuestion());
            etReponse.setText(""); // Réinitialiser le champ de réponse
        }
    }

    private void afficherResultats() {
        Intent intent = new Intent(this, GameResultsActivity.class);
        intent.putExtra("JOUEURS", new ArrayList<>(jeu.getJoueurs()));
        startActivity(intent);
        finish();
    }

    private String[] getNomsJoueurs() {
        String[] noms = new String[jeu.getJoueurs().size()];
        for (int i = 0; i < noms.length; i++) {
            noms[i] = jeu.getJoueurs().get(i).getNom();
        }
        return noms;
    }

    private void validerReponse() {
        String reponseJoueur = etReponse.getText().toString().trim();
        if (reponseJoueur.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une réponse", Toast.LENGTH_SHORT).show();
            return;
        }

        Joueur joueurActuel = jeu.getJoueurActuel();
        boolean bonneReponse = jeu.verifierReponse(questionActuelle, reponseJoueur);

        // Afficher la bonne réponse dans tous les cas
        String message = bonneReponse ? 
            "Bonne réponse !" : 
            "Mauvaise réponse. La bonne réponse était : " + questionActuelle.getReponse();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Ajouter la catégorie seulement si la réponse est correcte et que la catégorie n'est pas déjà gagnée
        if (bonneReponse && !jeu.categorieDejaGagnee(questionActuelle.getCategorie(), joueurActuel)) {
            joueurActuel.ajouterCategorie(questionActuelle.getCategorie());
            mettreAJourScores();

            // Vérifier si le joueur a gagné
            if (jeu.joueurAGagne(joueurActuel)) {
                Toast.makeText(this, joueurActuel.getNom() + " a gagné !", Toast.LENGTH_LONG).show();
                afficherResultats();
                return;
            }
        }

        // Passer au joueur suivant seulement s'il y a plus d'un joueur
        if (jeu.getNombreJoueurs() > 1) {
            jeu.passerAuJoueurSuivant();
        }
        
        // Afficher la prochaine question
        afficherQuestionAleatoire();
    }
} 