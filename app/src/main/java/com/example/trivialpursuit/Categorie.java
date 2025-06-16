package com.example.trivialpursuit;

public class Categorie {
    private String nom;
    private int couleur;

    public static final Categorie GEOGRAPHIE = new Categorie("Géographie", 0xFF0000FF); // Bleu
    public static final Categorie HISTOIRE = new Categorie("Histoire", 0xFFFF0000); // Rouge
    public static final Categorie DIVERTISSEMENT = new Categorie("Divertissement", 0xFFFFD700); // Jaune
    public static final Categorie ART_LITTERATURE = new Categorie("Art et Littérature", 0xFF800080); // Violet
    public static final Categorie SCIENCES_NATURE = new Categorie("Sciences et Nature", 0xFF008000); // Vert
    public static final Categorie SPORTS_LOISIRS = new Categorie("Sports et Loisirs", 0xFFFFA500); // Orange

    public static final Categorie[] TOUTES_CATEGORIES = {
        GEOGRAPHIE, HISTOIRE, DIVERTISSEMENT, ART_LITTERATURE, SCIENCES_NATURE, SPORTS_LOISIRS
    };

    private Categorie(String nom, int couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public int getCouleur() {
        return couleur;
    }
} 