package com.example.trivialpursuit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Joueur implements Serializable {
    private String nom;
    private int score;
    private List<String> categoriesGagnees;

    public Joueur(String nom) {
        this.nom = nom;
        this.score = 0;
        this.categoriesGagnees = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public void ajouterPoints(int points) {
        this.score += points;
    }

    public void ajouterCategorie(String categorie) {
        if (!categoriesGagnees.contains(categorie)) {
            categoriesGagnees.add(categorie);
        }
    }

    public List<String> getCategoriesGagnees() {
        return categoriesGagnees;
    }

    public boolean aGagneToutesCategories() {
        return categoriesGagnees.size() == 6; // 6 catégories dans le Trivial Pursuit
    }
} 