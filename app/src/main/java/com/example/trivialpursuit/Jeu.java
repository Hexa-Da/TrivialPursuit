package com.example.trivialpursuit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jeu {
    private List<Question> questions;
    private List<Joueur> joueurs;
    private int joueurActuel;
    private static final String[] CATEGORIES = {
        "Géographie", "Histoire", "Divertissement",
        "Art & Littérature", "Sciences & Nature", "Sports & Loisirs"
    };

    public Jeu() {
        this.questions = new ArrayList<>();
        this.joueurs = new ArrayList<>();
        this.joueurActuel = 0;
        initialiserQuestions();
    }

    private void initialiserQuestions() {
        // Exemple de questions (à compléter avec plus de questions)
        questions.add(new Question(
            "Quelle est la capitale de la France ?",
            new String[]{"Londres", "Berlin", "Paris", "Madrid"},
            2,
            "Géographie"
        ));
        // Ajouter plus de questions ici
    }

    public void ajouterJoueur(Joueur joueur) {
        joueurs.add(joueur);
    }

    public Question getQuestionAleatoire(String categorie) {
        List<Question> questionsCategorie = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCategorie().equals(categorie)) {
                questionsCategorie.add(q);
            }
        }
        if (questionsCategorie.isEmpty()) return null;
        Random random = new Random();
        return questionsCategorie.get(random.nextInt(questionsCategorie.size()));
    }

    public Joueur getJoueurActuel() {
        return joueurs.get(joueurActuel);
    }

    public void passerAuJoueurSuivant() {
        joueurActuel = (joueurActuel + 1) % joueurs.size();
    }

    public String[] getCategories() {
        return CATEGORIES;
    }

    public boolean verifierReponse(Question question, int reponse) {
        return question.getBonneReponse() == reponse;
    }
} 